package com.w77996.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class Redis1 extends RedisConfigProperties {

    @Value("${redis.password}")
    private String password;


    @Bean("jdisConnectionFactory")
    @ConfigurationProperties(prefix = "redis")
    @Primary
    @Override
    protected JedisConnectionFactory jdisConnectionFactory() {
       return  super.jdisConnectionFactory();
    }

    @Override
    protected Integer redisDatabase() {
        return 5;
    }

    @Override
    protected String redisPassword() {
        return this.password;
    }

//    @Bean("jedisClientConfiguration")
//    @Override
//    protected JedisClientConfiguration jedisClientConfiguration() {
//        return super.jedisClientConfiguration();
//    }

    @Bean("redisTemplate")
    @Override
    protected RedisTemplate redisTemplate() {
        return super.redisTemplate();
    }
}
