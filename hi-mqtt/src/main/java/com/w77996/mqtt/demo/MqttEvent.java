package com.w77996.mqtt.demo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * topic事件
 * @author WJH
 * @date 2019/5/920:16
 */
@Getter
public class MqttEvent extends ApplicationEvent {

    /**
     *
     */
    private String topic;
    /**
     * 发送的消息
     */
    private String message;

    public MqttEvent(Object source,String topic,String message) {
        super(source);
        this.topic = topic;
        this.message = message;
    }

}

