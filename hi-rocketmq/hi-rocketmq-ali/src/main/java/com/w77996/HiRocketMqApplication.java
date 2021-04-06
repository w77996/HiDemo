package com.w77996;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HiRocketMqApplication {


    public static void main(String[] args) {
        SpringApplication.run(HiRocketMqApplication.class);

//        rocketmqProducerService.sendMsg("whh test");
    }
}
