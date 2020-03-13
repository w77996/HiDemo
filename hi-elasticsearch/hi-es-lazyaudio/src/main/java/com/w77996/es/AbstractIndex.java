package com.w77996.es;

import com.google.common.collect.Maps;
import com.w77996.factory.ESFactory;
import com.w77996.scan.ScanCursor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.mapper.MapperService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 索引抽象实现
 */
@Slf4j
public abstract class AbstractIndex<T extends ScanCursor<D>, D extends Collection> implements IIndex {


    /**
     * 创建索引
     *
     * @param indexName
     * @return
     */
    @Override
    public boolean createIndex(String indexName) {
        boolean success = false;
        try {
            log.info("创建索引 "+indexName+" ...");
            // settings
            XContentBuilder settings = setting();

            // 创建索引
            CreateIndexRequest createIndexRequest = Requests.createIndexRequest(indexName);
            createIndexRequest.settings(settings);
            //获取设置的mapping
            Map<String, XContentBuilder> mappings = mappings();
            Set<String> types = mappings.keySet();
            for (String type : types) {
                createIndexRequest.mapping(type, mappings.get(type));
            }

//            CreateIndexResponse createIndexResponse = ESFactory.getClient(indexName).admin().indices().create(createIndexRequest).actionGet(30, TimeUnit.SECONDS);
            CreateIndexResponse createIndexResponse = ESFactory.getInstance().getClient().admin().indices().create(createIndexRequest).actionGet(30, TimeUnit.SECONDS);

            success = createIndexResponse.isAcknowledged();
            return success;
        } catch (Throwable e) {
            throw new RuntimeException("创建索引 " + indexName + " 异常", e);
        } finally {
            log.info(" ------  创建索引 "+indexName+" "+(success ? "成功" : "失败")+"    ------  ");
        }
    }

    @Override
    public void rebuild(String indexName) throws Exception {

    }

    /**
     * 索引 setting 设置
     * @return
     * @throws Exception
     */
    protected XContentBuilder setting() throws Exception {
        XContentBuilder settings = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("analysis")
                .startObject("analyzer")
                .startObject("default")
                .field("type", getAnalyzerType())
                .endObject()
                .endObject()
                .endObject()
                .field("compound_format", true)
                .field("refresh_interval", -1)
                .field("number_of_shards", getShardsSize())
                .field("number_of_replicas", 0)
                .field("max_result_window", 50000)
                .field("search.slowlog.threshold.query.warn", "1s")
                .field("search.slowlog.threshold.query.info", "500ms")
                .field("indexing.slowlog.threshold.index.warn", "1s")
                .field("indexing.slowlog.threshold.index.info", "500ms")
                .field("indexing.slowlog.threshold.index.debug", "200ms")
                .field("indexing.slowlog.threshold.index.trace", "100ms")
                .endObject();
        return settings;
    }



    protected abstract XContentBuilder mapping() throws Exception;

    protected Map<String, XContentBuilder> mappings() throws Exception {
        Map<String, XContentBuilder> mappings = Maps.newHashMap();
        mappings.put(MapperService.DEFAULT_MAPPING, mapping());
        return mappings;
    }

    /**
     * 分词类型
     *
     * @return
     */
    protected String getAnalyzerType(){
        return "ik_max_word";
    }

    /**
     * 分片数量
     *
     * @return
     */
    protected int getShardsSize(){
        return 1;
    }

    /**
     * 获取索引主键名
     * @return
     */
    protected abstract String getIndexIdName();

    /**
     * 加载数据
     * @param tableName
     * @param ids
     * @return
     */
    protected abstract D queryData(String tableName, List<String> ids);

    /**
     * 加载数据
     * @param tableName
     * @param scanCursor 游标，首次为null
     * @param size
     * @return
     */
    protected abstract T queryData(String tableName, T scanCursor, int size);

    /**
     * 转换数据
     * @param tableName
     * @param dataList
     * @return
     * @throws Exception
     */
    protected abstract List<Map<String,Object>> transformData(String tableName, D dataList) throws Exception;

    /**
     * 根据索引ID获取表名
     * @param indexId
     * @return
     */
    protected abstract String getTableName(String indexId);

    /**
     * 根据表名获取 type
     * @param tableName
     * @param id
     * @return
     */
    protected abstract String type(String tableName, String id);

    /**
     * 列举所有的表名
     * @return
     */
    protected abstract List<String> getAllTableName();

    /**
     * 更新数据源源来自kafka消息体，默认返回false
     * @return
     */
    protected boolean dataFromMessageBody(){
        return false;
    }

    /**
     * 如果更新源来自kafka消息体，需要重写此方法
     * @return
     */
    protected D parseData(Set<Object> objects){
        return null;
    }
}
