package com.w77996.factory;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ShardOperationFailedException;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequestBuilder;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequestBuilder;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.metadata.AliasOrIndex;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.network.InetAddresses;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

@Slf4j
public class ESFactory {

    private static final String HOST_SPLIT = ";";

    private static final String HOST_INFO_SPLIT = ":";

    private static final int DEF_PORT = 9300;

    private static ESFactory esFactory;

    private Client client;

    /**
     * es获取实例
     *
     * @return
     */
    public static synchronized ESFactory getInstance(){
        if( esFactory == null){
            ESFactory esFactory = new ESFactory();
            String esClusterName = "es-cluster";
            String esTransportAddress = "192.168.1.10:9200";
            log.info("es cluster name {}, transport address {}", esClusterName, esTransportAddress);
            esFactory.buildESNode(esClusterName, esTransportAddress);
            // bulidESNode可能会失败，所以需要先创建再赋值
            ESFactory.esFactory = esFactory;
        }
        return esFactory;
    }

    /**
     * 加载地址
     *
     * @param address
     * @return
     */
    private List<TransportAddress> loadAddress(String address){
        List<TransportAddress> addressList = new ArrayList<TransportAddress>();
        String[] addressArray = address.split(HOST_SPLIT);
        for(int i=0; i<addressArray.length; i++){
            if(StringUtils.isBlank(addressArray[i])){
                continue;
            }
            String[] hostInfo = addressArray[i].split(HOST_INFO_SPLIT);
            if(hostInfo.length<1 || hostInfo.length>2){
                throw new RuntimeException(String.format("ES集群连接节点配置错误，错误值：%s", address));
            }
            String host = hostInfo[0].trim();
            int port = (hostInfo.length==2 && StringUtils.isNotBlank(hostInfo[1])) ? Integer.parseInt(hostInfo[1].trim()) : DEF_PORT;
            addressList.add(new TransportAddress(InetAddresses.forString(host), port));
        }
        if(addressList.isEmpty()){
            throw new RuntimeException(String.format("未配置ES集群连接节点"));
        }
        return addressList;
    }

    /**
     * 创建node
     *
     * @param esClusterName
     * @param esTransportAddress
     */
    private void buildESNode(String esClusterName, String esTransportAddress){
        Settings.Builder settings = Settings.builder();
        if(StringUtils.isBlank(esClusterName)){
            throw new RuntimeException("未配置ES集群名字");
        }
        settings.put("cluster.name", esClusterName);
        // 设置
        settings.put("node.master", false);
        settings.put("node.data", false);
        settings.put("bootstrap.memory_lock", false);
        settings.put("http.enabled", false);

//		ImmutableSettings.Builder settings = ImmutableSettings.settingsBuilder().loadFromSource("elasticsearch.yml");

        System.setProperty("es.set.netty.runtime.available.processors", "false");
        TransportClient client = new PreBuiltTransportClient(settings.build());
        //获取集群地址
        List<TransportAddress> addressList = loadAddress(esTransportAddress);
        //添加集群地址
        for(TransportAddress address : addressList){
            client.addTransportAddress(address);
        }

        log.info(esClusterName + " status : " + client.admin().cluster().clusterStats(client.admin().cluster().prepareClusterStats().request()).actionGet().getStatus());

        this.client = client;
    }

    /**
     * 获取ES客户端
     * @return
     */
    public Client getClient(){
        return client;
    }

    /**
     * 关闭ES客户端，关闭后，该ES接口不能再调用
     */
    public void close(){
        // 关闭前 Flush 一下索引，Flush失败不会影响ES的使用，所以错误不需处理
        try{
            FlushResponse flushResponse = client.admin().indices().flush(new FlushRequest()).actionGet();
            if(flushResponse.getFailedShards()>0){
                log.warn("Flush 索引时有执行失败的分片，失败分片数：{}", flushResponse.getFailedShards());
            }
        }catch (Exception e){
            log.error("Flush 索引失败！[该异常不用处理]", e);
        }
        client.close();
    }

    /**
     * 优化索引
     * @param indexName 索引名
     * @param maxNumSegments 段数量限制
     * @return
     */
    public boolean optimize(String indexName, Integer maxNumSegments){
        long startTime = System.currentTimeMillis();
        log.info("优化"+indexName+"，索引端数量限制："+(maxNumSegments==null ? "无限制" : maxNumSegments));
        IndicesAdminClient indices = client.admin().indices();
        ForceMergeRequestBuilder forceMergeRequestBuilder = indices.prepareForceMerge(indexName);
        if(maxNumSegments!=null && maxNumSegments>0){
            forceMergeRequestBuilder.setMaxNumSegments(maxNumSegments);
        }
        ForceMergeResponse forceMergeResponse = indices.forceMerge(forceMergeRequestBuilder.request()).actionGet();
        boolean sucess = false;
        if(forceMergeResponse.getFailedShards()>0){
            ShardOperationFailedException[] shardFailures = forceMergeResponse.getShardFailures();
            StringBuilder sb = new StringBuilder();
            for(ShardOperationFailedException shardFailure : shardFailures){
                sb.append("index:");
                sb.append(shardFailure.index());
                sb.append(" shardId:");
                sb.append(shardFailure.shardId());
                sb.append(" status:");
                sb.append(shardFailure.status());
                sb.append(" reason:");
                sb.append(shardFailure.reason());
                sb.append("\r\n");
            }
            log.error("优化索引过程中，出现部分分片优化失败，索引名："+indexName +" 总分片数："+forceMergeResponse.getTotalShards()+" 失败分片数："+forceMergeResponse.getFailedShards()+"\r\n失败详细信息：\r\n"+sb.toString());
        }else{
            sucess = true;
        }
        log.error(" ------  优化"+indexName+"，执行结果 "+sucess+"，累计执行 "+((System.currentTimeMillis()-startTime)/1000)+" 秒    ------  ");
        return sucess;
    }

    public boolean setReplicas(String indexName, int replicas){
        IndicesAdminClient indices = client.admin().indices();
        GetSettingsResponse getSettingsResponse = indices.getSettings(indices.prepareGetSettings(indexName).request()).actionGet();
        Settings settings = getSettingsResponse.getIndexToSettings().get(indexName);
        if(settings==null){
            return false;
        }

        Settings.Builder settingsBuilder = Settings.builder();
        settingsBuilder.put("index.number_of_replicas", replicas);
        settingsBuilder.put("index.refresh_interval", "60s");
        UpdateSettingsRequestBuilder settingsRequest = indices.prepareUpdateSettings(indexName);
        settingsRequest.setSettings(settingsBuilder.build());
        UpdateSettingsResponse updateSettingsResponse = indices.updateSettings(settingsRequest.request()).actionGet();

        return updateSettingsResponse.isAcknowledged();

//		ImmutableSettings.Builder settingsBuilder = ImmutableSettings.builder().put(settings);
//		settingsBuilder.remove("index.number_of_shards");
//		settingsBuilder.remove("index.creation_date");
//		settingsBuilder.remove("index.version.created");
//		settingsBuilder.remove("index.uuid");
//		settingsBuilder.remove("index.analysis.analyzer.default.type");
//		settingsBuilder.put("index.number_of_replicas", replicas);
//		settingsBuilder.put("index.refresh_interval", "60s");
//		UpdateSettingsRequestBuilder settingsRequest = indices.prepareUpdateSettings(indexName);
//		settingsRequest.setSettings(settingsBuilder.build());
//		UpdateSettingsResponse updateSettingsResponse = indices.updateSettings(settingsRequest.request()).actionGet();
//		return updateSettingsResponse.isAcknowledged();
    }

    /**
     * 删除索引
     * @param indexName
     */
    public boolean deleteIndex(String indexName){
        IndicesAdminClient indices = getClient().admin().indices();
        IndicesExistsResponse indicesExistsResponse = indices.exists(indices.prepareExists(indexName).request()).actionGet();
        if(indicesExistsResponse.isExists()){
            DeleteIndexResponse deleteIndexResponse = indices.delete(indices.prepareDelete(indexName).request()).actionGet();
            if(deleteIndexResponse.isAcknowledged()){
                log.info("删除索引成功，索引名："+indexName);
                return true;
            }else{
                log.info("删除索引失败，索引名："+indexName);
                return false;
            }
        }else{
            return true;
        }
    }

    /**
     * 等待集群进入正常状态
     */
    public void waitClusterNormal(){
        log.info("等待ES集群进入可运行状态... ");
        ClusterHealthResponse clusterHealthResponse = getClient().admin().cluster().health(new ClusterHealthRequest().waitForYellowStatus()).actionGet();
        if (!isClusterNormal()) {
            ClusterHealthStatus status = clusterHealthResponse.getStatus();
            log.info("ES集群未进入可运行状态... 状态：" + status);
        } else {
            log.info("  --------  ES集群进入可运行状态      ----------  ");
        }
    }

    public boolean isClusterNormal(){
        ClusterHealthResponse clusterHealthResponse = getClient().admin().cluster().health(new ClusterHealthRequest()).actionGet();
        return clusterHealthResponse.getStatus() != ClusterHealthStatus.RED;
    }

    /**
     * 根据别名获取索引名
     *    没有映射关系时返回空集合
     * @param aliase
     * @return
     */
    public static List<String> getIndexByAliase(String aliase){
        List<String> indexNameList = new ArrayList<String>();

        SortedMap<String, AliasOrIndex> lookup = ESFactory.getInstance().getClient().admin().cluster().prepareState().execute().actionGet().getState().getMetaData().getAliasAndIndexLookup();
        for (Map.Entry<String, AliasOrIndex> entry : lookup.entrySet()) {
            String key = entry.getKey();
            AliasOrIndex aoi = entry.getValue();
            if (aliase.equals(key) && aoi.isAlias()) {
                List<IndexMetaData> indexs = aoi.getIndices();
                for (IndexMetaData indexMetaData : indexs) {
                    indexNameList.add(indexMetaData.getIndex().getName());
                }
            }
        }

        return indexNameList;
    }
}
