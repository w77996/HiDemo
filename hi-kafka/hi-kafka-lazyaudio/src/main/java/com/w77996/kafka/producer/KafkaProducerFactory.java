package com.w77996.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

@Slf4j
public class KafkaProducerFactory {

    private static Producer<Integer, String> stringProducer;

    private static Producer<Integer, byte[]> byteProducer;

//    public static void rebuild(){
//        rebuildStringProducer();
//       // rebuildByteProducer();
//    }

//    private synchronized static void rebuildStringProducer() {
//        try{
//            if(stringProducer != null) {
//                stringProducer.close();
//            }
//            Properties props = new Properties();
//            props.put("serializer.class", "kafka.serializer.StringEncoder");
//            props.put("metadata.broker.list", "");
//            props.put("request.required.acks", "-1");
//            props.put("producer.type", "sync");
//            stringProducer = new Producer<Integer, String>(new ProducerConfig(props));
//            log.info("rebuild stringProducer success");
//        }catch(Throwable t){
//            log.info("rebuild stringProducer error", t);
//        }
//    }

}
