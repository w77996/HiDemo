package com.w77996;

import com.w77996.sender.KafkaSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @description:
 * @author: w77996
 * @create: 2018-11-05 12:52
 **/
@SpringBootApplication
public class HiKafkaApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext  context = SpringApplication.run(HiKafkaApplication.class, args);

        KafkaSender sender = context.getBean(KafkaSender.class);

        for (int i = 0; i < 3; i++) {
            //调用消息发送类中的消息发送方法
            sender.send();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
