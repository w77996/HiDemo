package com.w77996.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @program: HiDemo
 * @description:
 * @author: w77996
 * @create: 2019-12-25 19:17
 */
@SpringBootApplication
@EnableConfigurationProperties
public class HiKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiKafkaApplication.class);
    }
}
