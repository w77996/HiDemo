//package com.w77996.redis.config;
//
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.time.Duration;
//
//@Configuration
//public class RedisConfig {
//    //
////    @Bean("jedisConnFactory0")
////    public JedisConnectionFactory jedisConnectionFactory(){
////        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
////        JedisConnectionFactory factory = new JedisConnectionFactory();
////        factory.setHostName("localhost");
////        factory.setUsePool(true);
////        factory.setPassword("12345");
////        factory.setDatabase(1);
////        factory.setPort(6379);
////        factory.setPoolConfig(jedisPoolConfig);
////        return factory;
////    }
////
////    @Bean("redisTemplate2")
////    public RedisTemplate redisTemplate(@Qualifier("jedisConnFactory0") JedisConnectionFactory jedisConnectionFactory){
////        RedisTemplate redisTemplate = new RedisTemplate();
////        redisTemplate.setEnableDefaultSerializer(true);
////        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
////        RedisConnectionFactory factory = jedisConnectionFactory;
////        redisTemplate.setConnectionFactory(factory);
////        return redisTemplate;
////    }
////    @Bean
////    public RedisTemplate initRedisTemplate(@Qualifier("connectionFactory") JedisConnectionFactory jedisConnectionFactory) {
////
////        RedisSerializer str = new StringRedisSerializer();
////        RedisSerializer json = new GenericJackson2JsonRedisSerializer();
////        RedisTemplate redisTemplate = new RedisTemplate();
////        redisTemplate.setConnectionFactory(jedisConnectionFactory);
////        redisTemplate.setKeySerializer(str);
////        redisTemplate.setValueSerializer(json);
////        redisTemplate.setHashKeySerializer(str);
////        redisTemplate.setHashValueSerializer(json);
////        return redisTemplate;
////    }
////
////    @Bean(name = "connectionFactory")
////    public JedisConnectionFactory initConnect() {
////        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
////        redisStandaloneConfiguration.setHostName("localhost");
////        redisStandaloneConfiguration.setPort(6379);
////        redisStandaloneConfiguration.setDatabase(0);
////        redisStandaloneConfiguration.setPassword(RedisPassword.of("12345"));
////
////        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
////        jedisClientConfiguration.connectTimeout(Duration.ofMillis(30000));//  connection timeout
////        jedisClientConfiguration.usePooling();
////        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
////
////        return factory;
////    }
//    @Bean(name="getJedisClientConfiguration")
//
//    public JedisClientConfiguration getJedisClientConfiguration() {
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder JedisPoolingClientConfigurationBuilder = (
//                JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        GenericObjectPoolConfig GenericObjectPoolConfig = new GenericObjectPoolConfig();
//        GenericObjectPoolConfig.setMaxIdle(1000);
//        GenericObjectPoolConfig.setMaxTotal(100);
//        GenericObjectPoolConfig.setMinIdle(100);
//        return JedisPoolingClientConfigurationBuilder.poolConfig(GenericObjectPoolConfig).build();
//    }
//
//    @Qualifier("getJedisConnectionFactoryOne ")
//    @Bean(name="getJedisConnectionFactoryOne")
//    @ConfigurationProperties(prefix = "redis")
//    public JedisConnectionFactory getJedisConnectionFactoryOne(@Qualifier("getJedisClientConfiguration") JedisClientConfiguration jedisClientConfiguration) {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
////        redisStandaloneConfiguration.setDatabase(0);
////        redisStandaloneConfiguration.setHostName("localhost");
////        redisStandaloneConfiguration.setPassword(RedisPassword.of("12345"));
////        redisStandaloneConfiguration.setPort(6379);
//        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
//    }
//
////    @Bean(value = "stringRedisTemplate")
////    public RedisTemplate getStringRedisTemplate() {
////        RedisTemplate redisTemplate = new RedisTemplate();
////        redisTemplate.setKeySerializer(new StringRedisSerializer());
////        redisTemplate.setValueSerializer(new StringRedisSerializer());
////        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
////        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
////        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
////        return redisTemplate;
////    }
//
//    @Bean(name = "objectRedisTemplate")
//    public RedisTemplate getObjectRedisTemplate(@Qualifier("getJedisConnectionFactoryOne") JedisConnectionFactory jedisConnectionFactory) {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        return redisTemplate;
//    }
//
//
//}
