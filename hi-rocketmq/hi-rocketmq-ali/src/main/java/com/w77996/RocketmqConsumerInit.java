package com.w77996;
import java.util.Properties;

import javax.annotation.PostConstruct;

import com.aliyun.openservices.ons.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * rocketmq消费者启动初始化类
 * 同一个消费者可以消费不同的topic，同一个topic可以被不同的消费者消费（启用该方式相当于集群消费中的广播消费）
 * @author hqc
 * @Date 2019年1月17日
 *
 */
@Component
public class RocketmqConsumerInit {

    private Logger logger = LoggerFactory.getLogger(RocketmqConsumerInit.class);

    @Value("${rocketmq.consumer.consumerId}")
    private String consumerId;

    @Value("${rocketmq.consumer.accessKey}")
    private String accessKey;

    @Value("${rocketmq.consumer.secretKey}")
    private String secretKey;

    @Value("${rocketmq.consumer.onsAddr}")
    private String ONSAddr;

    @Value("${rocketmq.consumer.msgTopic}")
    private String msgTopic;

    public static final String tag = "*";

    private static Consumer consumer;

    //public static final String topic2 = "Test2";//测试第二个消费主题


    @PostConstruct
    public void init(){
        System.out.println("初始化启动消费者者！");
        // consumer 实例配置初始化
        Properties properties = new Properties();
        //您在控制台创建的consumer ID
        properties.setProperty(PropertyKeyConst.GROUP_ID, consumerId);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        //设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接入域名(此处以公共云生产环境为例)，设置 TCP 接入域名，进入 MQ 控制台的消费者管理页面，在左侧操作栏单击获取接入点获取
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, ONSAddr);
        consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(msgTopic, tag, new RocketmqTestListener(){
            @Override
            public Action consume(Message message, ConsumeContext context) {
                try {
                    Long startTime = System.currentTimeMillis();

                    byte[] body = message.getBody();
                    String msg = new String(body);//获取到接收的消息，由于接收到的是byte数组，所以需要转换成字符串

                    //TODO 业务逻辑，自行设计
                    //testDao.insertDatas();//持久层，这里不再展述，自行补全
                    System.out.println(msg);
                    Long endTime = System.currentTimeMillis();
                    System.out.println("单次消费耗时："+(endTime-startTime)/1000);
                } catch (Exception e) {
                    logger.error("MessageListener.consume error:" + e.getMessage(), e);
                }

                logger.info("MessageListener.Receive message");
                //如果想测试消息重投的功能,可以将Action.CommitMessage 替换成Action.ReconsumeLater
                return Action.CommitMessage;
            }
        });//监听第一个topic，new对应的监听器
//	    consumer.subscribe(topic2, tag, new RocketmqTest2Listener());//监听另外一个topic，new对应的监听器
        // 在发送消息前，必须调用start方法来启动consumer，只需调用一次即可，当项目关闭时，自动shutdown
        consumer.start();
        logger.info("ConsumerConfig start success.");

    }

    /**
     * 初始化消费者
     * @return
     */
    public Consumer getconsumer(){
        return consumer;
    }
}