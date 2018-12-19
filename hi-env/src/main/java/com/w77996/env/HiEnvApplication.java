package com.w77996.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 环境打包
 * @author: w77996
 * @create: 2018年12月18日 18:03
 **/
@SpringBootApplication
@RestController
public class HiEnvApplication {
    public static void main(String[] args) {
        SpringApplication.run(HiEnvApplication.class, args);
    }

    @Value("${env}")
    public String env;

    @GetMapping("/env")
    public String env() {
        return env;
    }


}
