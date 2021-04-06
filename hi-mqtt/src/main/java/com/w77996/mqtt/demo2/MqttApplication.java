package com.w77996.mqtt.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName MqttApplication
 * @Description
 * @author wuhaihui
 * @date 2020/12/28 11:03
 */
@SpringBootApplication
public class MqttApplication {

    public static void main(String[] args) {

        SpringApplication.run(MqttApplication.class);
        test();
        test3();
    }


    private static void test(){
        MqttPushClient.MQTT_HOST = "tcp://111.229.120.71:1883";
        MqttPushClient.MQTT_CLIENTID = "client123";
        MqttPushClient.MQTT_USERNAME = "admin";
        MqttPushClient.MQTT_PASSWORD = "public";
             MqttPushClient client = MqttPushClient.getInstance();
            client.subscribe("TEST");
    }

    private static void test3(){
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MqttPushClient.MQTT_HOST = "tcp://111.229.120.71:1883";
            MqttPushClient.MQTT_CLIENTID = "client1234";
            MqttPushClient.MQTT_USERNAME = "admin";
            MqttPushClient.MQTT_PASSWORD = "public";
            MqttPushClient client = MqttPushClient.getInstance();
            client.publish("TEST","whh");
        }

    }
}
