package com.w77996.dubbo.comsumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class HiDubboConsumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiDubboConsumeApplication.class, args);
    }
}
