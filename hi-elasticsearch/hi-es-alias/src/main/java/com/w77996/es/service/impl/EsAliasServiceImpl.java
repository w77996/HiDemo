package com.w77996.es.service.impl;

import com.w77996.es.entity.User;
import com.w77996.es.entity.UserIndex1;
import com.w77996.es.entity.UserIndex2;
import com.w77996.es.service.EsAliasService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.AliasBuilder;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EsAliasServiceImpl implements EsAliasService {


    private String USER_INDEX = "";

    @Value("${es.alias}")
    private String USER_INDEX_ALIAS;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public void createIndex() {
        try {
            //  Map<String, XContentBuilder> mappings = Maps.newHashMap();
            //  elasticsearchTemplate.getClient().admin().indices().prepareCreate(USER_INDEX).execute().actionGet();
            elasticsearchTemplate.indexExists(USER_INDEX);
            XContentBuilder settings = XContentFactory.jsonBuilder()
                    .startObject()
//                    .startObject("analysis")
//                    .startObject("analyzer")
//                    .startObject("default")
//                    .field("type", "ik_max_word")
//                    .endObject()
//                    .endObject()
//                    .endObject()
//                    .startObject("properties")
//                    .startObject("id").field("type", "long").endObject()
//                    .startObject("username").field("type", "string").endObject()
                    // .endObject()
                    .field("compound_format", true)
                    .field("refresh_interval", -1)
                    .field("number_of_shards", 2)
                    .field("number_of_replicas", 0)
                    .field("max_result_window", 50000)
                    .field("search.slowlog.threshold.query.warn", "1s")
                    .field("search.slowlog.threshold.query.info", "500ms")
                    .field("indexing.slowlog.threshold.index.warn", "1s")
                    .field("indexing.slowlog.threshold.index.info", "500ms")
                    .field("indexing.slowlog.threshold.index.debug", "200ms")
                    .field("indexing.slowlog.threshold.index.trace", "100ms")
                    .endObject();

            // mappings.put(MapperService.DEFAULT_MAPPING, settings);
            // PutMappingRequest mapping = Requests.putMappingRequest(USER_INDEX).type(USER_INDEX).source(settings);
            // 创建索引
            CreateIndexRequest createIndexRequest = Requests.createIndexRequest(USER_INDEX);
            createIndexRequest.settings(settings);

            CreateIndexResponse createIndexResponse = elasticsearchTemplate.getClient().admin().indices().create(createIndexRequest).actionGet(30, TimeUnit.SECONDS);
            log.info(createIndexResponse.isAcknowledged() + "");

            log.info(settings.toString());
            // elasticsearchTemplate.createIndex(USER_INDEX);
            // elasticsearchTemplate.createIndex(USER_INDEX, mappings);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean createIndexByTemplate() {
        XContentBuilder settings = null;
        try {
            settings = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("compound_format", true)
                    .field("refresh_interval", -1)
                    .field("number_of_shards", 2)
                    .field("number_of_replicas", 0)
                    .field("max_result_window", 50000)
                    .field("search.slowlog.threshold.query.warn", "1s")
                    .field("search.slowlog.threshold.query.info", "500ms")
                    .field("indexing.slowlog.threshold.index.warn", "1s")
                    .field("indexing.slowlog.threshold.index.info", "500ms")
                    .field("indexing.slowlog.threshold.index.debug", "200ms")
                    .field("indexing.slowlog.threshold.index.trace", "100ms")
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //判断user_1索引是否存在
        boolean userIndex1exist = elasticsearchTemplate.indexExists(UserIndex1.class);
        if (userIndex1exist) {
            boolean success = elasticsearchTemplate.deleteIndex(USER_INDEX);


            log.info("del index " + success);

        }
        elasticsearchTemplate.createIndex(UserIndex1.class, settings);
        log.info("create index UserIndex1" );
        List<IndexQuery> queries = new ArrayList<IndexQuery>();
        for (int i = 1; i < 200; i++) {
            UserIndex1 user = new UserIndex1(i,"user_1"+ i);

            IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("user_1")
                    .withType("user").withId(i + StringUtils.EMPTY).withObject(user).build();
            queries.add(indexQuery);
        }
        elasticsearchTemplate.bulkIndex(queries);
        //默认索引1
        AliasQuery aliasQuery = new AliasBuilder()
                .withIndexName("user_1")
                .withAliasName("alias_user").build();
       boolean addAlias =  elasticsearchTemplate.addAlias(aliasQuery);
        log.info("addAlias "+addAlias );
        //判断user_2索引是否存在
        boolean userIndex2exist = elasticsearchTemplate.indexExists(UserIndex2.class);
        if (userIndex2exist) {
            boolean success = elasticsearchTemplate.deleteIndex(UserIndex2.class);
            log.info("del index " + success);
        }
        elasticsearchTemplate.createIndex(UserIndex2.class, settings);
        List<IndexQuery> queries1 = new ArrayList<IndexQuery>();
        for (int i = 1; i < 200; i++) {
            UserIndex1 user = new UserIndex1(i,"user_2"+ i);

            IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("user_2")
                    .withType("user").withId(i + StringUtils.EMPTY).withObject(user).build();

            queries1.add(indexQuery);
        }
        elasticsearchTemplate.bulkIndex(queries1);


        return true;
    }

    @Override
    public void deleteIndex() {
        elasticsearchTemplate.deleteIndex(USER_INDEX);
    }

    @Override
    public boolean rebuildIndex() {
        return false;
    }

    @Override
    public void addData(String user1) {
//        List<IndexQuery> queries = new ArrayList<IndexQuery>();
//        for (int i = 1; i < 200; i++) {
//            UserIndex1 user = new User(i,  + i);
//
//            IndexQuery indexQuery = new IndexQueryBuilder().withIndexName(USER_INDEX_ALIAS)
//                    .withType(USER_INDEX).withId(i + StringUtils.EMPTY).withObject(user).build();
//            queries.add(indexQuery);
//        }
//        elasticsearchTemplate.bulkIndex(queries);
    }

    @Override
    public List<User> list() {
        return null;
    }
}
