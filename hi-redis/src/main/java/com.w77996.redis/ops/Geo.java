package com.w77996.redis.ops;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.Point;

@RestController
@RequestMapping("/geo")
@Slf4j
public class Geo {

    @Autowired
    private RedisTemplate redisTemplate;

    String cityGeoKey = "cityGeo ";
//    @GetMapping
//    public String geo(){
//        Long addedNum = redisTemplate.opsForGeo()
//                .add(cityGeoKey,new Point(new Double(116.405285),new Double(39.904989)),"北京");
//    }
}
