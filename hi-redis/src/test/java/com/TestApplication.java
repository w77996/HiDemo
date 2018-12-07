package com;

import com.w77996.redis.RedisApplication;
import com.w77996.redis.config.Redis2;
import javafx.application.Application;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: 测试
 * @author: w77996
 * @create: 2018-12-05 16:37
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Redis2.class})
public class TestApplication {

    @Qualifier("redisTemplateTwo")
    @Autowired
    RedisTemplate redisTemplateTwo;

    @Test
    public void testAsync() {
        System.out.println("=====" + Thread.currentThread().getName() + "=========");
        System.out.println("=====" + redisTemplateTwo.opsForValue().get("c_cam_item_c1_1665905") + "=========");
    }



}
