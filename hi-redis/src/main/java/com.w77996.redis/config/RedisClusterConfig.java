//package com.w77996.redis.config;
//
//import com.w77996.redis.properties.RedisClusterProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Slf4j
//@Component
//public class RedisClusterConfig {
//
//    @Autowired
//    private RedisClusterProperties redisClusterProperties;
//
//
//    @Bean
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(redisClusterProperties.getMaxIdle());
//        jedisPoolConfig.setMinIdle(redisClusterProperties.getMinIdle());
//
//        jedisPoolConfig.setTestOnBorrow(true);
//
//        return jedisPoolConfig;
//    }
//
//    @Bean
//    public JedisCluster jedisCluster(JedisPoolConfig jedisPoolConfig) {
//
////        if (StringUtils.isEmpty(host)) {
////            LOGGER.info("redis集群主机未配置");
////            throw new LocalException("redis集群主机未配置");
////        }
////        if (StringUtils.isEmpty(port)) {
////            LOGGER.info("redis集群端口未配置");
////            throw new LocalException("redis集群端口未配置");
////        }
//        String[] cNodes = redisClusterProperties.getNodes().split(",");
//        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
//        // 分割出集群节点
//        for (String node : cNodes) {
//            String[] hp = node.split(":");
//            nodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
//        }
//
//        return new JedisCluster(nodes, 5000, 1500, 3, "12345", jedisPoolConfig);
//    }
//}
