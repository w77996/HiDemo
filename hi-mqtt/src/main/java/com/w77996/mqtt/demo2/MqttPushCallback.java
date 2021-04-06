package com.w77996.mqtt.demo2;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @ClassName MqttPushCallback
 * @Description
 * @author wuhaihui
 * @date 2020/12/28 11:04
 */
@Slf4j
public class MqttPushCallback implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) {
      log.info("断开连接");
      MqttPushClient.getInstance().connect();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        log.info("topic " + s);
        log.info("mqttMessage {}", JSON.toJSONString(mqttMessage));

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info(iMqttDeliveryToken.isComplete() + "");
    }
}
