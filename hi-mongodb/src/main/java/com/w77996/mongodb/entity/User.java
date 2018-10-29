package com.w77996.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @description: 实体类
 * @author: w77996
 * @create: 2018-10-19 10:11
 **/
@Document(collection = "user")
@AllArgsConstructor
@Data
public class User {
    @Id
    private Integer id;
    @Field("name")
    private String name;
    @Field("age")
    private Integer age;
}
