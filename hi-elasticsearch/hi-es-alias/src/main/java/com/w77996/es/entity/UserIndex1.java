package com.w77996.es.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "user_1",type = "user", shards = 1, replicas = 0)

public class UserIndex1 extends User {

    public UserIndex1(Integer id ,String username){
        super(id,username);
    }
}
