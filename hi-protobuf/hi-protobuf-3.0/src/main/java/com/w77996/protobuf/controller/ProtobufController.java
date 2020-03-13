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
//
//    public static void main(String[] args) {
//        String i = "\\xAC\\xED\\x00\\x05ur\\x00\\x02[B\\xAC\\xF3\\x17\\xF8\\x06\\x08T\\xE0\\x02\\x00\\x00xp\\x00\\x00\\x00\\x91\\x08{\\x10{\\x1A\\x07account\"\\x03123*\\x05email2\\x03234@\\x01Z\\x03123`\\x01h\\x8E\\xAB\\xB1\\xCB\\xD3-r\\x09backcover\\x82\\x01\\x03tag\\x90\\x01\\x01\\xA0\\x01\\x01\\xAA\\x01\\x06remark\\xB8\\x01\\x8E\\xAB\\xB1\\xCB\\xD3-\\xC0\\x01\\x8E\\xAB\\xB1\\xCB\\xD3-\\xD2\\x01\\x05ecode\\xD8\\x01\\x01\\xEA\\x01\\x05vover\\xF2\\x01\\x07feature\\xF8\\x01\\x01\\x80\\x02\\x01\\x88\\x02\\x01\\x90\\x02\\x01\\x98\\x02\\x01\\xA2\\x02\\x04uuid";
//        String x = "\\xAC\\xED\\x00\\x05ur\\x00\\x02[B\\xAC\\xF3\\x17\\xF8\\x06\\x08T\\xE0\\x02\\x00\\x00xp\\x00\\x00\\x00\\x91\\x08{\\x10{\\x1A\\x07account\"\\x03123*\\x05email2\\x03234@\\x01Z\\x03123`\\x01h\\x8E\\xAB\\xB1\\xCB\\xD3-r\\x09backcover\\x82\\x01\\x03tag\\x90\\x01\\x01\\xA0\\x01\\x01\\xAA\\x01\\x06remark\\xB8\\x01\\x8E\\xAB\\xB1\\xCB\\xD3-\\xC0\\x01\\x8E\\xAB\\xB1\\xCB\\xD3-\\xD2\\x01\\x05ecode\\xD8\\x01\\x01\\xEA\\x01\\x05vover\\xF2\\x01\\x07feature\\xF8\\x01\\x01\\x80\\x02\\x01\\x88\\x02\\x01\\x90\\x02\\x01\\x98\\x02\\x01\\xA2\\x02\\x04uuid";
//
//    }
//}
