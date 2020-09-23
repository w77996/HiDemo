package com.w77996.redisson;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName HiRedissonApplication
 * @Description
 * @author wuhaihui
 * @date 2020/9/23 19:15
 */
@SpringBootApplication
public class HiRedissonApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiRedissonApplication.class);
    }

//    /**
//     * 哨兵模式自动装配
//     * @return
//     */
//    @Bean
//    @ConditionalOnProperty(name="redisson.master-name")
//    RedissonClient redissonSentinel() {
//        Config config = new Config();
//        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redissonProperties.getSentinelAddresses())
//                .setMasterName(redissonProperties.getMasterName())
//                .setTimeout(redissonProperties.getTimeout())
//                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
//                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());
//
//        if(StringUtils.isNotBlank(redissonProperties.getPassword())) {
//            serverConfig.setPassword(redissonProperties.getPassword());
//        }
//        return Redisson.create(config);
//    }

    /**
     * 单机模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="redisson.address")
    RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setTimeout(10000)
                .setConnectionPoolSize(100)
                .setConnectionMinimumIdleSize(100);


        return Redisson.create(config);
    }


}
