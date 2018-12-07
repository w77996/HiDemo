//package com.w77996.redis.config;
//
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//public class RedisConfig2 {
//    @Bean(name = "getJedisClientConfigurationTwo")
//    public JedisClientConfiguration getJedisClientConfiguration2() {
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder JedisPoolingClientConfigurationBuilder = (
//                JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        GenericObjectPoolConfig GenericObjectPoolConfig = new GenericObjectPoolConfig();
////        GenericObjectPoolConfig.setMaxIdle(1000);
////        GenericObjectPoolConfig.setMaxTotal(100);
////        GenericObjectPoolConfig.setMinIdle(100);
//        return JedisPoolingClientConfigurationBuilder.poolConfig(GenericObjectPoolConfig).build();
//    }
//
//    @Bean(name = "getJedisConnectionFactoryTwo")
//    @ConfigurationProperties(prefix = "redis2")
//    public JedisConnectionFactory getJedisConnectionFactory2(@Qualifier("getJedisClientConfigurationTwo") JedisClientConfiguration jedisClientConfiguration) {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        System.out.println(redisStandaloneConfiguration.getDatabase());
////        redisStandaloneConfiguration.setDatabase(0);
////        redisStandaloneConfiguration.setHostName("localhost");
////        redisStandaloneConfiguration.setPassword(RedisPassword.of("12345"));
////        redisStandaloneConfiguration.setPort(6379);
//        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
//    }
//
//
//    @Bean(name = "objectRedisTemplateTwo")
//    public RedisTemplate getObjectRedisTemplate2(@Qualifier("getJedisConnectionFactoryTwo") JedisConnectionFactory jedisConnectionFactory) {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        return redisTemplate;
//    }
//}
