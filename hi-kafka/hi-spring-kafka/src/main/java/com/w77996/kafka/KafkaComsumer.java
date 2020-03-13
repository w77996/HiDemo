package com.w77996.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @program: HiDemo
 * @description:
 * @author: w77996
 * @create: 2019-12-25 19:37
 */
@Component
@Slf4j
public class KafkaComsumer {


    @KafkaListener(topics = {"canal-event-topic"},containerFactory = "batchFactory")
    public void listen(ConsumerRecord<?, ?> consumerRecord) {

//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//
//        if (kafkaMessage.isPresent()) {
//
//            Object message = kafkaMessage.get();
//
//            log.info("----------------- record =" + record);
//            log.info("------------------ message =" + message);
//        }
        log.info("Consumer->topic:{}, value:{}", consumerRecord.topic(), consumerRecord.value());

    }
}
