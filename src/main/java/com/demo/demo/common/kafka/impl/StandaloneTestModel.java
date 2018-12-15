package com.demo.demo.common.kafka.impl;

import com.demo.demo.common.kafka.EventModel;
import com.demo.demo.common.kafka.consts.EventTypeEnum;
import com.demo.demo.common.kafka.consts.KafkaTopicEnum;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by liuyang on 2018/11/9
 */
@Component
public class StandaloneTestModel implements EventModel<Map<String, Integer>> {

    @Override
    public void handle(Map<String, Integer> map, ConsumerRecord<String, String> record) {
        System.out.println();
        System.out.println("topic -----> " + record.topic());
        System.out.println(map);
    }

    @Override
    public KafkaTopicEnum getTopic() {
        return KafkaTopicEnum.TEST;
    }

    @Override
    public EventTypeEnum getEventType() {
        return EventTypeEnum.EXCLUSIVE;
    }
}
