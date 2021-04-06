package com.w77996.mqtt.demo;
/**
 * @author WJH
 * @date 2019/4/1111:39
 */
public enum TopicName {
    ROLL_CALL_DEFAULT(1,"listenDefault");

    private final Integer key;
    private final String value;

    private TopicName(Integer key,String value){
        this.key = key;
        this.value = value;
    }
    public Integer getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
