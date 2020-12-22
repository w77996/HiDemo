package com.w77996.rocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.protocol.FlatMessage;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

public class SyncComsumer  {
    public static void main(String[] args) throws InterruptedException, MQClientException {

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("API_MQ_GROUP");

        // Specify name server addresses.
        consumer.setNamesrvAddr("52.83.153.55:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("example", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
//                System.out.println("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                for(Message message : msgs){
                    byte[] data = message.getBody();
                    FlatMessage flatMessage = JSON.parseObject(data, FlatMessage.class);
                    System.out.println( JSONObject.toJSON(flatMessage));


                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
