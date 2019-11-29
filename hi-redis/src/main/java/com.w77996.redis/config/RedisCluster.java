//package com.w77996.redis.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.w77996.redis.properties.RedisClusterProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisNode;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//@Slf4j
//public class RedisCluster {
//
//    @Qualifier("redisClusterProperties")
//    @Autowired
//    private RedisClusterProperties redisClusterProperties;
//
//    /**
//     * 配置 Redis 连接池信息
//     */
//    @Bean
//    public JedisPoolConfig getJedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(redisClusterProperties.getMaxIdle());
//        jedisPoolConfig.setMaxWaitMillis(redisClusterProperties.getMaxWait());
//        jedisPoolConfig.setTestOnBorrow(redisClusterProperties.isTestOnBorrow());
//        return jedisPoolConfig;
//    }
//
////    @Bean
////    public JedisCluster getJedisCluster() {
////        String[] cNodes = credisClusterProperties.getNodes().split(",");
////        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
////        // 分割出集群节点
////        for (String node : cNodes) {
////            String[] hp = node.split(":");
////            nodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
////        }
////        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
////        jedisPoolConfig.setMaxIdle(maxIdle);
////        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
////        // 创建集群对象
////        JedisCluster jedisCluster = new JedisCluster(nodes, timeout, timeout, maxAttempts, jedisPoolConfig);
////        return jedisCluster;
////    }
//
//
//    /**
//     * 配置 Redis Cluster 信息
//     */
//    @Bean
//    public RedisClusterConfiguration getJedisCluster() {
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        redisClusterConfiguration.setMaxRedirects(redisClusterProperties.getMaxRedirects());
//        redisClusterConfiguration.setPassword(RedisPassword.of("12345"));
//        List<RedisNode> nodeList = new ArrayList<>();
//        String[] cNodes = redisClusterProperties.getNodes().split(",");
//        //分割出集群节点
//        for(String node : cNodes) {
//            String[] hp = node.split(":");
//            nodeList.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
//            log.info(node);
//        }
//        redisClusterConfiguration.setClusterNodes(nodeList);
//
//        return redisClusterConfiguration;
//    }
//
//    /**
//     * 配置 Redis 连接工厂
//     */
//    @Bean
//    public JedisConnectionFactory getJedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration, JedisPoolConfig jedisPoolConfig) {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
//        return jedisConnectionFactory;
//    }
//
//    /**
//     * 设置数据存入redis 的序列化方式
//     *  redisTemplate序列化默认使用的jdkSerializeable
//     *  存储二进制字节码，导致key会出现乱码，所以自定义序列化类
//     */
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
//
//        return redisTemplate;
//    }
//
//}
