package com.w77996.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class Redis3 extends RedisConfigProperties {

    @Value("${redis.password}")
    private String password;

    @Bean("jdisConnectionFactory3")
    @ConfigurationProperties(prefix = "redis3")
    @Override
    protected JedisConnectionFactory jdisConnectionFactory() {
        return  super.jdisConnectionFactory();
    }

    @Override
    protected Integer redisDatabase() {
        return 7;
    }

    @Override
    protected String redisPassword() {
        return this.password;
    }

//    @Bean("jedisClientConfiguration3")
//    @Override
//    protected JedisClientConfiguration jedisClientConfiguration() {
//        return super.jedisClientConfiguration();
//    }

    @Bean("redisTemplate3")
    @Override
    protected RedisTemplate redisTemplate() {
        return super.redisTemplate();
    }
}
