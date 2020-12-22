package com.w77996.rocket;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartListener implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-consumer_test-topic-2");

        // Specify name server addresses.
        consumer.setNamesrvAddr("52.83.153.55:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("test-topic-2", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.print(" Receive New Messages:"+ msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        new Thread(()->{

            try {
                consumer.start();
            } catch (MQClientException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.printf("Consumer Started.%n");
    }
}
