package com.w77996.redis.ops;

import com.w77996.redis.core.BaseRedisDaoInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cluster")
@Slf4j
@RestController
public class OpsCluster {

    @Autowired
    BaseRedisDaoInter redisTemplate;

    @GetMapping("/cluster")
    public void cluster(){
        redisTemplate.set("111","111");
        log.info(redisTemplate.get("111").toString());
    }
}
