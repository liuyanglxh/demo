package com.demo.demo.common.kafka.consts;

/**
 * 统一维护kafka的主题
 */
public enum KafkaTopicEnum {
    TEST("test"),
    USER_CHANGE("user.change");

    KafkaTopicEnum(String topic) {
        this.topic = topic;
    }

    private String topic;

    public String topic() {
        return topic;
    }

}
