package com.w77996.receiver;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @description:
 * @author: w77996
 * @create: 2018-11-05 12:52
 **/
@Component
@Slf4j
public class KafkaReceiver {

    @KafkaListener(topics = {"canal-event-topic"})
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
