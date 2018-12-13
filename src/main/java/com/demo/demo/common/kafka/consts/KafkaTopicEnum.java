package com.demo.demo.common.kafka.consts;

/**
 * kafka 所有的topic都放到这里维护，避免不小心重复
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
