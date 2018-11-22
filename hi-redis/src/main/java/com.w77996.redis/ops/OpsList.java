package com.w77996.redis.ops;

import com.w77996.redis.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 操作list
 * @author: w77996
 * @create: 2018-11-22 20:42
 **/
@RestController
@RequestMapping("/list")
@Slf4j
public class OpsList {

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/ops")
    public void opsList() {
        redisTemplate.opsForList().leftPush("list", "a");
        redisTemplate.opsForList().leftPush("list", "b");
        redisTemplate.opsForList().leftPush("list", "c");

        //获取集合指定位置的值。
        String listValue = redisTemplate.opsForList().index("list", 1) + "";
        log.info("通过index(K key, long index)方法获取指定位置的值:" + listValue);

        List<Object> list =  redisTemplate.opsForList().range("list",0,-1);
        log.info("通过range(K key, long start, long end)方法获取指定范围的集合值:"+list);

        Object popValue = redisTemplate.opsForList().leftPop("list");
        log.info("通过leftPop(K key)方法移除的元素是:" + popValue);
        list =  redisTemplate.opsForList().range("list",0,-1);
        log.info(",剩余的元素是:" + list);

        popValue = redisTemplate.opsForList().rightPop("list");
        log.info("通过rightPop(K key)方法移除的元素是:" + popValue);
        list =  redisTemplate.opsForList().range("list",0,-1);
        log.info(",剩余的元素是:" + list);
    }
}
