package com.demo.demo.common.kafka;

import com.demo.demo.common.SpringContextHolder;
import com.demo.demo.common.kafka.consts.EventTypeEnum;
import com.demo.demo.common.kafka.consts.KafkaTopicEnum;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Created by liuyang on 2018/11/9
 * kafka model
 */
public interface EventModel<T> {

    /**
     * 消费消息的逻辑
     */
    void handle(T t, ConsumerRecord<String, String> record);

    /**
     * 需要监听的topic
     */
    KafkaTopicEnum getTopic();

    /**
     * 消息模式：单播、广播
     */
    default EventTypeEnum getEventType(){
        return EventTypeEnum.EXCLUSIVE;
    }

    /**
     * 异步发送消息发送消息
     */
    default void sendAsync(T t){
        EventManager manager = SpringContextHolder.getBean(EventManager.class);
        String topic = this.getTopic().topic();
        manager.sendAsync(topic, t, getEventType());
    }

    /**
     * 同步发送消息
     */
    default boolean sendSync(T t){
        EventManager manager = SpringContextHolder.getBean(EventManager.class);
        String topic = this.getTopic().topic();
        return manager.sendSync(topic, t, getEventType());
    }

}
