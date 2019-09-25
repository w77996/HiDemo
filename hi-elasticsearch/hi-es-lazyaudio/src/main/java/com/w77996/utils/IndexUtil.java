package com.w77996.utils;

import com.w77996.es.IIndex;
import com.w77996.factory.ESFactory;
import com.w77996.index.IndexContext;
import com.w77996.index.OnlineIndex;
import com.w77996.index.RebuildIndex;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class IndexUtil {

    //作为一个常量key，当需要的时候，在data里设置，不存储此值
    public final static String PARENT = "es_type_parent";


    /**
     * 索引数据
     * @param dataList
     * @throws IOException
     */
    public void index(String indexName, IType type, String tableName, String idKey, List<Map<String,Object>> dataList) throws IOException{
        if(CollectionUtils.isEmpty(dataList)){
            return ;
        }else if(dataList.size()==1){
            // 单条索引
            index(indexName, type.type(tableName, dataList.get(0).get(idKey)), idKey, dataList.get(0));
        }else{
            // 批量索引
            List<IndexRequest> requestList = makeIndexRequest(indexName, type, tableName, idKey, dataList);
            BulkRequestBuilder bulkRequest = ESFactory.getInstance().getClient().prepareBulk();
            for(IndexRequest indexRequest : requestList){
                bulkRequest.add(indexRequest);
            }
            //超时控制
            BulkResponse bulkResponse = bulkRequest.execute().actionGet(60, TimeUnit.SECONDS);
            if (bulkResponse.hasFailures()) {
                BulkItemResponse[] responses = bulkResponse.getItems();
                for(int i=0; i<responses.length; i++){
                    if(responses[i].isFailed()){
                        log.error(responses[i].getFailureMessage());
                        log.error("INDEX_FAIL, table=" + type + " data=" + dataList.get(i));
                    }
                }
            }
        }
    }



    public static interface IType{
        /**
         * 根据表名获取 type
         * @param tableName
         * @param id
         * @return
         */
        public String type(String tableName, Object id);

    }
    /**
     * 索引数据
     * @throws IOException
     */
    public boolean index(String indexName, String type, String idKey, Map<String,Object> data) throws IOException{
        IndexRequest requestList = makeIndexRequest(indexName, type, idKey, data);
        requestList.timeout(TimeValue.timeValueSeconds(30));
        IndexResponse indexResponse = ESFactory.getInstance().getClient().index(requestList).actionGet(30, TimeUnit.SECONDS);
        if (!(indexResponse.status() == RestStatus.CREATED)) {
            log.error("INDEX_FAIL, table=" + type + " data=" + data);
        }
        return indexResponse.status() == RestStatus.CREATED;
    }

    /**
     * 构建索引请求
     * @param type
     * @param idKey
     * @param dataList
     * @return
     * @throws IOException
     */
    public List<IndexRequest> makeIndexRequest(String indexName, IType type, String tableName, String idKey, List<Map<String,Object>> dataList) throws IOException{
        List<IndexRequest> requestList = new ArrayList<IndexRequest>(dataList.size());
        for(Map<String,Object> data : dataList){
            requestList.add(makeIndexRequest(indexName, type.type(tableName, data.get(idKey)), idKey, data));
        }
        return requestList;
    }

    /**
     * 构建索引请求
     * @param type
     * @param idKey
     * @param data
     * @return
     */
    public IndexRequest makeIndexRequest(String indexName, String type, String idKey, Map<String, Object> data)
            throws IOException {
        IndexRequest indexRequest = Requests.indexRequest(indexName);
        indexRequest.type(type);
        indexRequest.id(data.get(idKey).toString());
        if(data.get(PARENT) != null) {
            indexRequest.parent(data.get(PARENT).toString());
        }
        XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
        jsonBuilder.startObject();
        for(String key : data.keySet()){
            if(PARENT.equals(key)){
                continue;
            }
            jsonBuilder.field(key, data.get(key));
        }
        jsonBuilder.endObject();
        indexRequest.source(jsonBuilder);
        return indexRequest;
    }

}
