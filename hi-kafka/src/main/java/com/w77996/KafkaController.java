package com.w77996;

import com.w77996.sender.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: w77996
 * @create: 2018-11-05 13:33
 **/
@RestController
public class KafkaController {

//    @Autowired
//    KafkaSender kafkaSender;

    @GetMapping("/kafka")
    public String kafka() {
//        for (int i = 0; i < 3; i++) {
//            //调用消息发送类中的消息发送方法
//            kafkaSender.send();
//
////            try {
////                Thread.sleep(3000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }
        return "ok";
    }

}
