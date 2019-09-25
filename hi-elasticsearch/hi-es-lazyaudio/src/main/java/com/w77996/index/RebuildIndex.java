package com.w77996.index;

import com.alibaba.fastjson.JSONObject;
import com.w77996.factory.ESFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RebuildIndex {

    private static final AtomicInteger REBUILD_INDEX_COUNT = new AtomicInteger();

    private final AtomicBoolean isRuning;

    /**
     * 索引上下文
     */
    private final IndexContext indexContext;

    /**
     * 重放日志是否追上的检查值
     */
    private final int replayLogCheck;

    /**
     * 重建索引名
     */
    private String rebuildIndexName;

    private final AtomicBoolean isPauseOnline = new AtomicBoolean(false);

    public RebuildIndex(IndexContext indexContext){

        this.indexContext = indexContext;
        this.replayLogCheck = 100;
        this.isRuning = new AtomicBoolean(false);
        log.info("构建 RebuildIndex 负责 "+indexContext.getIndex().getIndexName()+" 索引业务，"+"处理"+indexContext.getIndex().getTableNames()+"表的数据");
    }

    /**
     * 执行索引重建任务
     * @return
     */
    public boolean runRebulidJob(){

        long startTime = System.currentTimeMillis();

//        if(!indexContext.isMaster()){
//            log.error(" ------  非主Search工程，不进行索引重建     ------  ");
//            return false;
//        }

        synchronized (this) {
            // 1. 检查是否已经在重建索引
            if(isRuning.get()){
                log.error(" ------  并发控制，重建索引任务已经在执行     ------  ");
                // 已经在运行，直接返回
                return false;
            }

            // 2. 设置为运行状态
            isRuning.set(true);
        }

        try{
            while(true){
                if(REBUILD_INDEX_COUNT.incrementAndGet()>3){
                    REBUILD_INDEX_COUNT.decrementAndGet();
                    log.error("有其它索引在重建，等待中...");
                    Thread.sleep(60*1000);
                }else{
                    break;
                }
            }
            // 重建索引
            log.error("开始执行重建索引任务 ...");
            doRebuildIndex();
            log.info(" ------  重建索引任务执行成功，累计执行 "+((System.currentTimeMillis()-startTime)/1000)+" 秒    ------  ");
            return true;
        }catch(Throwable t){
            log.info("rebuild index fail", t);
            log.info(" ------  重建索引任务执行失败，累计执行 "+((System.currentTimeMillis()-startTime)/1000)+" 秒    ------  ");
            return false;
        }finally{
            // 临时状态清理
            rebuildIndexName = null;

            // 在线索引状态位至位
            if(indexContext.isChangeLogDoing()){
                try {
                    indexContext.stopChangeLog();
                } catch (Throwable t) {
                    log.error("关闭 change log 执行失败", t);
                }
            }

            if(isPauseOnline.get()){
               // indexContext.keepRuningOnlineIndex();
                isPauseOnline.set(false);
            }

            // 设置为停止态
            isRuning.set(false);

            REBUILD_INDEX_COUNT.decrementAndGet();
        }
    }

    /**
     * 初始化环境
     * @throws Exception
     */
    private void init() throws Exception{
        log.info("初始化");
        log.info("刷新索引名映射关系");
        if(!indexContext.refreshIndexName()){
           // throw new IndexException("刷新索引映射关系失败");
            return;
        }

        rebuildIndexName = indexContext.getPhysicalRebuildIndexName();

        log.info("初始化重建索引环境，当前重建索引名："+rebuildIndexName);
        log.info("创建索引，索引名："+rebuildIndexName);
        boolean isCreate = false;
        try{
            isCreate = indexContext.getIndex().createIndex(rebuildIndexName);
        }catch(Throwable t){
            log.info("创建索引失败，本次失败可以不处理，将会自动重试 ...");
        }
        if(!isCreate){
            // 多实例运行时, 暂时通过检查索引是否在增长判断是否有重建索引任务在运行
            IndicesExistsResponse existsResponse = ESFactory.getInstance().getClient().admin().indices().exists(Requests.indicesExistsRequest(rebuildIndexName)).actionGet(30, TimeUnit.SECONDS);
            if(existsResponse.isExists()){
                // count api Deprecated in 2.1.0
                long count1 = ESFactory.getInstance().getClient().prepareSearch(rebuildIndexName).setSize(0).get(TimeValue.timeValueSeconds(60)).getHits().getTotalHits();
                try {
                    Thread.sleep(1000*15);
                } catch (Exception e) {
                }
                long count2 = ESFactory.getInstance().getClient().prepareSearch(rebuildIndexName).setSize(0).get(TimeValue.timeValueSeconds(60)).getHits().getTotalHits();
                // 该索引检查方案不精确，以后还是应该换成同一的分布式任务控制方案
                if(count2>count1){
                    //throw new IndexException("重建索引任务失败，已有其它search实例在进行重建索引任务");
                    return;
                }
                // 删除索引
                log.info("删除索引，索引名："+rebuildIndexName);
                if(!ESFactory.getInstance().deleteIndex(rebuildIndexName)){
                    //throw new IndexException("删除索引失败，索引名："+rebuildIndexName);
                    return;
                }
            }
            if(!indexContext.getIndex().createIndex(rebuildIndexName)){
               // throw new IndexException("创建索引失败，索引名："+rebuildIndexName);
                return;
            }
        }
    }

    /**
     * 重建索引
     * @return
     * @throws IOException
     */
    private void doRebuildIndex() throws Exception {

        init();

        log.info("设置在线索引记录数据变更日志");
        indexContext.startChangeLog();

        // 7. 重建索引
        log.info("全量索引数据库上的数据 ...");
        long startRebulidTime = System.currentTimeMillis();
        rebuild();
        log.info(" ------  完成全量索引数据库上的数据,对应索引"+rebuildIndexName+"，耗时"+((System.currentTimeMillis()-startRebulidTime)/1000)+" 秒    ------  ");

        // 8. 索引优化      -- 是否调到变更重放完毕后做优化
        log.info("优化索引 ...");
        long startOptimizeTime = System.currentTimeMillis();
        ESFactory.getInstance().optimize(rebuildIndexName, 1);
        log.info(" ------  完成"+rebuildIndexName+"索引优化，耗时 "+((System.currentTimeMillis()-startOptimizeTime)/1000)+" 秒    ------  ");

        // TODO 字符集设置
        BufferedReader logReader = new BufferedReader(new FileReader(indexContext.getChangeLogFilePath()));

        // 10. 重放变更日志
        log.info("重放本地数据变更日志[第一阶段] ...");
        long startReplay1Time = System.currentTimeMillis();
        int replayChangeLogCount = replayChangeLogFirst(logReader);
        log.info(" ------  完成[第一阶段]的变更日志重放，行数"+replayChangeLogCount+" 耗时 "+((System.currentTimeMillis()-startReplay1Time)/1000)+" 秒    ------  ");

        // 11. 暂停在线索引
        log.info("暂停在线索引");
       // indexContext.pauseOnlineIndex();
        isPauseOnline.set(true);

        // 12. 设置 在线索引只做索引更新  以及  关闭 change log
        log.info("停止变更日志");
        indexContext.stopChangeLog();

        // 13. 继续重放 change log
        log.info("重放本地数据变更日志[第二阶段] ...");
        long startReplay2Time = System.currentTimeMillis();
        replayChangeLogCount = replayChangeLogCount + replayChangeLogSecond(logReader);
        if((indexContext.getWriteChangeLogCount()-replayChangeLogCount) != 0){
            log.error("变更日志，处于错误的状态，统计的日志行数："+indexContext.getWriteChangeLogCount()+", 但实际只有："+replayChangeLogCount);
        }
        log.info(" ------  完成[第二阶段]的变更日志重放，行数"+replayChangeLogCount+" 耗时 "+((System.currentTimeMillis()-startReplay2Time)/1000)+" 秒    ------  ");

        // 14. 删除变更日志， OnlineIndex.startChangeLog 有做环境清理，这里不执行

        // 执行索引切换流程
        // 16. 别名切换
        log.info("索引切换：将"+rebuildIndexName+"设置为线上索引");
        if(!indexContext.switchIndex(rebuildIndexName)){
           // throw new IndexException("索引切换失败：将"+rebuildIndexName+"设置为线上索引失败");
            return;
        }

        // 17. 运行在线索引
        log.info("运行在线索引");
        //indexContext.keepRuningOnlineIndex();
        isPauseOnline.set(false);

        // 18. 删除原有在线索引
        String oldOnlineIndexName = indexContext.getPhysicalRebuildIndexName();
        log.info("删除原有在线索引，索引名："+oldOnlineIndexName);
        if(!ESFactory.getInstance().deleteIndex(oldOnlineIndexName)){
           // throw new IndexException("删除索引失败，索引名："+oldOnlineIndexName);
            return;
        }

        log.info("简单优化索引 ...");
        long startSimpleOptimizeTime = System.currentTimeMillis();
        ESFactory.getInstance().optimize(rebuildIndexName, null);

        log.info(" ------  完成"+rebuildIndexName+"索引简单优化，耗时 "+((System.currentTimeMillis()-startSimpleOptimizeTime)/1000)+" 秒    ------  ");

        log.info("设置副本数 ...");
        //int replicas = IDCUtil.isDevelopment() || IDCUtil.isTest() ? 0 : 1;
        if(rebuildIndexName.startsWith("order")) {
            ESFactory.getInstance().setReplicas(rebuildIndexName, 1);
        }else{
            String replicasStr = "1";
            int replicas = 1;
            if(NumberUtils.isNumber(replicasStr)){
                replicas = NumberUtils.toInt(replicasStr);
            }
            ESFactory.getInstance().setReplicas(rebuildIndexName, replicas);
        }
    }

    /**
     * 重建索引
     *	      当重新索引数据失败时，抛出异常
     * @return
     */
    protected void rebuild() throws Exception {
        indexContext.getIndex().rebuild(rebuildIndexName);
    }

    /**
     * 重放变更日志 -- 第一阶段
     * @param logReader
     * @return
     * @throws IOException
     */
    private int replayChangeLogFirst(BufferedReader logReader)
            throws IOException{
        int replayChangeLogCount = 0;
//        List<KafkaMessage> messageList = new ArrayList<KafkaMessage>(1000);
//        while ((indexContext.getWriteChangeLogCount()-replayChangeLogCount) > replayLogCheck) {
//            String line = logReader.readLine();
//            if(line == null){
//                logger.error("变更日志，处于错误的状态，统计的日志行数："+indexContext.getWriteChangeLogCount()+", 但实际只有："+replayChangeLogCount);
//                break;
//            }
//            replayChangeLogCount ++;
//            if(StringUtils.isNotBlank(line)){
//                KafkaMessage message = JSONObject.parseObject(line, KafkaMessage.class);
//                messageList.add(message);
//                if(messageList.size()>=1000){
//                    indexContext.getIndex().index(rebuildIndexName, messageList);
//                    messageList = new ArrayList<KafkaMessage>(1000);
//                }
//            }
//        }
//        if(messageList.size()>=0){
//            indexContext.getIndex().index(rebuildIndexName, messageList);
//        }
        return replayChangeLogCount;
    }

    /**
     * 重放变更日志 -- 第二阶段
     * @param logReader
     * @return
     * @throws IOException
     * @throws IndexException
     */
    private int replayChangeLogSecond(BufferedReader logReader
    ) throws IOException{
        int replayChangeLogCount = 0;
//        List<KafkaMessage> messageList = new ArrayList<KafkaMessage>(1000);
//        while (true) {
//            String line = logReader.readLine();
//            if(line == null){
//                break;
//            }
//            replayChangeLogCount ++;
//            if(StringUtils.isNotBlank(line)){
//                KafkaMessage message = JSONObject.parseObject(line, KafkaMessage.class);
//                messageList.add(message);
//                if(messageList.size()>=1000){
//                    indexContext.getIndex().index(rebuildIndexName, messageList);
//                    messageList = new ArrayList<KafkaMessage>(1000);
//                }
//            }
//        }
//        if(messageList.size()>=0){
//            indexContext.getIndex().index(rebuildIndexName, messageList);
//        }
        return replayChangeLogCount;
    }

}
