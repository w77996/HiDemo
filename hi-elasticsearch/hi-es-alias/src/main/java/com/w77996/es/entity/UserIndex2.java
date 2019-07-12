package com.w77996.es.entity;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "user_2",type = "user", shards = 1, replicas = 0)
public class UserIndex2 extends User {
    public UserIndex2(Integer id ,String username){
        super(id,username);
    }
}
