package com.w77996.redis.ops;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bitmap")
public class BitMap {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public void setBitMap(){
        long key = 2019101114;
        long userId =208805162;
        //2019年10月14日用戶208805162簽到
        redisTemplate.opsForValue().setBit(key,userId,true);
        System.out.println(redisTemplate.opsForValue().getBit(key,userId));
        //(伪代码)这个方法可以统计2019年10月14日总共多少人签到，redisTemplate没有bitcount的api
        //System.out.println(redisTemplate.opsForValue().BitCount(key));

        //用户208805162在2019年10月14日簽到
        redisTemplate.opsForValue().setBit(userId,key,true);
        //取出用户签到的信息，对二进制进行计算，就能判断用户连续签到多少天
        System.out.println(redisTemplate.opsForValue().get(userId));
    }
}
