//package com.w77996.protobuf.controller;
//
//import com.w77996.protobuf.bean.SnsUserExtBean;
//import com.w77996.protobuf.bean.User;
//import com.w77996.protobuf.protobuf.ProtobufFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//
//@RestController
//@Slf4j
//public class ProtobufController {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @GetMapping
//    public String test(){
//        SnsUserExtBean user = new SnsUserExtBean();
//        user.setAblumnCount(1);
//        user.setAccountName("test");
//        user.setAreaIds("123");
//        user.setBackCover("backcover");
//        user.setBirthday(new Date(1568626464142L));
//        user.setCollectionFolderCount(1);
//        user.setCollectionReadCount(1);
//        user.setCover("vover");
//        user.setCreateTime(new Date(1568626464142L));
//        user.setEmail("email");
//        user.setFeatures("feature");
//        user.setFlag(1L);
//        user.setFolderCount(1);
//        user.setFollowCount(1);
//        user.setId(123);
//        user.setLikeTags("tag");
//        user.setNickName("nick");
//        user.setAccountName("account");
//        user.setClientUuid(1);
//        user.setRemark("remark");
//        user.setEncodeNickName("ecode");
//        user.setImeiRegTime(new Date(1568626464142L));
//        user.setUuids("uuid");
//        user.setUserId(123L);
//        user.setUpdateTime(new Date(1568626464142L));
//        user.setImeiRegUserId(123L);
//        user.setSex((byte) 1);
//        user.setPhoneNum("123");
//        user.setPassword("234");
//        user.setStatus(1);
//        user.setSource(1);
//        byte[] bytes = ProtobufFactory.toByteArray(user);
//        redisTemplate.opsForValue().set("test",bytes);
//        return "ok";
//    }
//
//    @GetMapping("/1")
//    public String test1(){
//        byte[] bytes = (byte[]) redisTemplate.opsForValue().get("test");
//        SnsUserExtBean user = ProtobufFactory.toObject4UserBean(bytes);
//        return user.toString();
//    }
//}
