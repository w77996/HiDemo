package com.w77996;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

/**
 * rocketmq生产者启动初始化类
 * @author sixmonth
 * @Date 2019年1月17日
 *
 */
@Component
public class RocketmqProducerInit {

    @Value("${rocketmq.producer.producerId}")
    private String producerId;

    @Value("${rocketmq.producer.accessKey}")
    private String accessKey;

    @Value("${rocketmq.producer.secretKey}")
    private String secretKey;

    @Value("${rocketmq.producer.onsAddr}")
    private String ONSAddr;

    private static Producer producer;

	  /*

	  //当无法注入实例的时候可以使用此方法进行实例初始化
	  private static class ProducerHolder {
	    private static final RocketmqProducerInit INSTANCE = new RocketmqProducerInit();
	  }

	  private RocketmqProducerInit (){

	  }

	  public static final RocketmqProducerInit getInstance() {
	    return ProducerHolder.INSTANCE;
	  }*/

    @PostConstruct
    public void init(){
        System.out.println("初始化启动生产者！");
        // producer 实例配置初始化
        Properties properties = new Properties();
        //您在控制台创建的Producer ID
        properties.setProperty(PropertyKeyConst.GROUP_ID, producerId);
//        properties.setProperty(PropertyKeyConst.ProducerId, "hi-product");
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        //设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接入域名(此处以公共云生产环境为例)，设置 TCP 接入域名，进入 MQ 控制台的消费者管理页面，在左侧操作栏单击获取接入点获取
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, ONSAddr);
        producer = ONSFactory.createProducer(properties);
        // 在发送消息前，初始化调用start方法来启动Producer，只需调用一次即可，当项目关闭时，自动shutdown
        producer.start();
    }

    /**
     * 初始化生产者
     * @return
     */
    public Producer getProducer(){
        return producer;
    }
}