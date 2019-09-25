package com.w77996.index;

import com.w77996.es.IIndex;
import com.w77996.factory.ESFactory;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.stats.CommonStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class IndexContext {

    /**
     * 日志目录
     */
    private final String logPath;

    /**
     * 在线索引处理器|增量索引处理器
     */
    private final OnlineIndex onlineIndex;

    /**
     * 重建索引处理器
     */
    private final RebuildIndex rebuildIndex;

    /**
     * 是否是A索引为线上索引
     */
    private final AtomicBoolean aOnline;

    private final IIndex index;

    public IndexContext(IIndex index){
        this.index = index;
        this.logPath = "es.replay.log.path";
        this.onlineIndex = new OnlineIndex(this);
        this.rebuildIndex = new RebuildIndex(this);
        //this.messageConsumer = messageConsumer;
        aOnline = new AtomicBoolean(true);
    }

    /**
     * 初始化
     */
    public boolean init(){
        log.info("初始化 ...");
        IndicesAdminClient indices = ESFactory.getInstance().getClient().admin().indices();
        // 1. 做索引映射关系刷新
        if(refreshIndexName()){
            // 2. 检查在线索引
            IndicesExistsResponse existsResponse = indices.exists(Requests.indicesExistsRequest(getPhysicalOnlineIndexName())).actionGet(30, TimeUnit.SECONDS);
            // 3. 线上索引存在
            if(existsResponse.isExists()){
                log.info("初始化成功");
                return true;
            }
        }

        log.error("初始化失败，执行映射关系修正 ...");
        // 存在多个索引映射时，从中选取一个存在的索引
        List<String> indexNameList = getIndexByAliase(index.getIndexName());
        if(indexNameList.isEmpty()){
            indexNameList.add(getPhysicalOnlineIndexName());
            indexNameList.add(getPhysicalRebuildIndexName());
        }
        // 获取默认线上索引名
        String onlineIndexName = indexNameList.get(0);
        // 使用数据量最多的一个作为线上索引
        long maxDocsCount = 0;
        for(String indexName : indexNameList){
            try{
                IndicesExistsResponse existsResponse = indices.exists(Requests.indicesExistsRequest(indexName)).actionGet(30, TimeUnit.SECONDS);
                if(existsResponse.isExists()){
                    IndicesStatsResponse indicesStatsResponse = indices.stats(indices.prepareStats(indexName).request()).actionGet(30, TimeUnit.SECONDS);
                    CommonStats primary = indicesStatsResponse.getPrimaries();
                    if(primary!=null && primary.getDocs().getCount()>maxDocsCount){
                        maxDocsCount = primary.getDocs().getCount();
                        onlineIndexName = indexName;
                    }
                }
            }catch(Throwable e){
                log.warn("读取索引信息出错", e);
            }
        }

        // 检查选中的线上索引是否真实存在
        IndicesExistsResponse existsResponse = indices.exists(Requests.indicesExistsRequest(onlineIndexName)).actionGet(30, TimeUnit.SECONDS);
        if(!existsResponse.isExists()){
            // 创建
            getIndex().createIndex(onlineIndexName);
        }

        // 将选中的索引设置成线上索引
        if(switchIndex(onlineIndexName)){
            log.error("映射关系修正成功，当前线上索引："+getPhysicalOnlineIndexName());
        }else{
            log.error("映射关系修正失败，当前线上索引："+getPhysicalOnlineIndexName());
        }
        return false;
    }

    /**
     * 切换索引
     * @param onlineIndexName
     * @return 切换成功返回true, 切换失败返回false
     */
    public boolean switchIndex(final String onlineIndexName){
        final String onlineIndexAliase = index.getIndexName();
        log.info("切换"+onlineIndexAliase+"["+onlineIndexName+"]线上索引 ...");
//		final String oldOnlineIndexName = getPhysicalOnlineIndexName();

        IndicesAdminClient indices = ESFactory.getInstance().getClient().admin().indices();
        IndicesAliasesRequestBuilder indicesAliasesRequestBuilder = indices.prepareAliases();
        // 移除原有别名
        List<String> indexNameList = getIndexByAliase(onlineIndexAliase);
        for(String name : indexNameList){
            indicesAliasesRequestBuilder.removeAlias(name, onlineIndexAliase);
        }
        // 建立新的别名
        indicesAliasesRequestBuilder.addAlias(onlineIndexName, onlineIndexAliase);

        IndicesAliasesResponse indicesAliasesResponse = indices.aliases(indicesAliasesRequestBuilder.request()).actionGet(30, TimeUnit.SECONDS);
        if(!indicesAliasesResponse.isAcknowledged()){
            log.error("切换"+onlineIndexAliase+"["+onlineIndexName+"]线上索引失败, 重试一次");
            indexNameList = getIndexByAliase(onlineIndexAliase);
            if(!(indexNameList.size()==1
                    && onlineIndexName.equals(indexNameList.get(0)))){
                indicesAliasesRequestBuilder = indices.prepareAliases();
                for(String name : indexNameList){
                    indicesAliasesRequestBuilder.removeAlias(name, onlineIndexAliase);
                }
                indicesAliasesRequestBuilder.addAlias(onlineIndexName, onlineIndexAliase);
                indicesAliasesResponse = indices.aliases(indicesAliasesRequestBuilder.request()).actionGet(30, TimeUnit.SECONDS);
                if(!indicesAliasesResponse.isAcknowledged()){
                    log.error(" ------  切换"+onlineIndexAliase+"["+onlineIndexName+"]线上索引失败     ------  ");
                }
            }
        }

        boolean success = false;
        // 刷新映射关系
        if(refreshIndexName()){
            // 读取别名
            indexNameList = getIndexByAliase(onlineIndexAliase);
            success = indexNameList.size()==1 && getPhysicalOnlineIndexName().equals(indexNameList.get(0));

        }
        log.error(" ------  切换"+onlineIndexAliase+"["+onlineIndexName+"]线上索引"+(success ? "成功" : "失败")+"     ------  ");
        return success;
    }

    public OnlineIndex getOnlineIndex() {
        return onlineIndex;
    }

    public RebuildIndex getRebuildIndex() {
        return rebuildIndex;
    }


    public IIndex getIndex() {
        return index;
    }

    /**
     * 获取在线索引物理索引名
     * @return
     */
    public String getPhysicalOnlineIndexName(){
        return aOnline.get() ? getIndexA() : getIndexB();
    }

    /**
     * 获取重建索引物理索引名
     * @return
     */
    public String getPhysicalRebuildIndexName(){
        return aOnline.get() ? getIndexB() : getIndexA();
    }

    /**
     * A索引名
     * @return
     */
    private String getIndexA(){
        return index.getIndexName() + "_a";
    }

    /**
     * B索引名
     * @return
     */
    private String getIndexB(){
        return index.getIndexName() + "_b";
    }

    /**
     * 刷新索引名称映射关系
     */
    public boolean refreshIndexName(){
        final String onlineIndexAliase = index.getIndexName();
        log.info("刷新"+onlineIndexAliase+"索引映射关系 ...");
        //获取别名
        List<String> indexNameList = getIndexByAliase(onlineIndexAliase);
        if(indexNameList.isEmpty()){
            log.error("错误的映射关系：线上逻辑索引" + onlineIndexAliase + "没有对应ES物理索引！");
            return false;
        }
        if(indexNameList.size()>1){
            log.error("错误的映射关系：线上逻辑索引" + onlineIndexAliase + "对应对应多个ES物理索引！"+indexNameList);
            return false;
        }
        aOnline.set(getIndexA().equals(indexNameList.get(0)));
        log.info(" ------  刷新"+onlineIndexAliase+"索引映射关系成功, 索引名"+onlineIndexAliase+" 对应线上索引"+getPhysicalOnlineIndexName()+" 重建索引"+getPhysicalRebuildIndexName()+" ------  ");
        return true;
    }

    private List<String> getIndexByAliase(String indexAliase) {
        return ESFactory.getIndexByAliase(indexAliase);
    }


    /**
     * 获取当前记录的变更日志行数
     * @return
     */
    public int getWriteChangeLogCount() {
        return this.onlineIndex.getWriteChangeLogCount();
    }


    /**
     * 是否在做变更日志
     * @return
     */
    public boolean isChangeLogDoing(){
        return this.onlineIndex.isChangeLogDoing();
    }

    /**
     * 获取变更日志文件路径，全路径
     *   已保证父路径一定存在
     * @return
     */
    public String getChangeLogFilePath(){
        File logPathFile = new File(logPath);
        if(!logPathFile.exists()){
            logPathFile.mkdirs();
        }
        return logPathFile.getAbsolutePath() + File.separator + index.getIndexName() + ".change.log";
    }

    /**
     * 记录数据变更日志
     * @return 是否执行成功， false：change log日志在调用前已经被开始
     * @throws IOException
     */
    public void startChangeLog() {
        this.onlineIndex.startChangeLog();
    }

    /**
     * 停止数据变更日志
     * @return 是否执行成功， false：change log日志在调用之前已经被停止
     */
    public void stopChangeLog() {
        this.onlineIndex.stopChangeLog();
    }
}
