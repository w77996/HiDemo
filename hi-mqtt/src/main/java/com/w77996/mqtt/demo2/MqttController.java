package com.w77996.mqtt.demo2;/**
 * @ClassName MqttController
 * @Description
 * @author wuhaihui
 * @date 2020/12/28 11:14
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MqttController
 * @Description
 * @author wuhaihui
 * @date 2020/12/28 11:14
 */
@RestController
@RequestMapping("/matt")
public class MqttController {


    @GetMapping
    public String mqtt(){
        MqttPushClient.MQTT_HOST = "tcp://111.229.120.71:1883";
        MqttPushClient.MQTT_CLIENTID = "client1234";
        MqttPushClient.MQTT_USERNAME = "admin";
        MqttPushClient.MQTT_PASSWORD = "public";
        MqttPushClient client = MqttPushClient.getInstance();
        client.publish("TEST","whh");
        return "ok";
    }
}
