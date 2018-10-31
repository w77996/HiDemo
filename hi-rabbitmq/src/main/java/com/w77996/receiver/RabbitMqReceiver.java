package com.w77996.receiver;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @description: 消费端
 * @author: w77996
 * @create: 2018-10-30 13:09
 **/
@Component
@Slf4j
public class RabbitMqReceiver {

//    /**
//     * DIRECT模式.
//     *
//     * @param message the message
//     * @param channel the channel
//     * @throws IOException the io exception  这里异常需要处理
//     */
//    @RabbitListener(queues = {"DIRECT_QUEUE"})
//    public void message(Message message, Channel channel) throws IOException {
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        log.debug("DIRECT "+new String (message.getBody()));
//    }

    /**
     * 监听替补队列 来验证死信.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception  这里异常需要处理
     */
    @RabbitListener(queues = {"REDIRECT_QUEUE"})
    public void redirect(Message message, Channel channel) throws IOException {
        log.info("88888888888888");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        log.info("dead message  10s 后 消费消息 {}",new String (message.getBody()));
    }
}
