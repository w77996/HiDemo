package com.w77996.mongodb.controller;

import com.w77996.mongodb.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: w77996
 * @create: 2018-10-19 11:11
 **/
@RestController
@Slf4j
public class MongoDBController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/save")
    public String ok(){
        List<User> list = new ArrayList<>();
        for(int i = 1; i< 200; i++){
            User news = new User(i,"名称"+i,10*i);
            list.add(news);
        }
        mongoTemplate.insert(list,User.class);
        log.info("success");
        return "success";
    }

    @GetMapping("/list")
    public List<User> list(){
        //根据用户查询所有符合条件的数据，返回List
        Query query = Query.query(Criteria.where("username").is("名称1"));
        List<User> users = mongoTemplate.find(query, User.class);
        //只查询符合条件的第一条数据，返回Article对象
        User user = mongoTemplate.findOne(query, User.class);
        //数量
        long count = mongoTemplate.count(query, User.class);
        //id查询
        user = mongoTemplate.findById(new ObjectId("1"), User.class);
        //所有的
        List<User> userList = mongoTemplate.findAll(User.class);
        return userList;
    }
}
