package com.demo.demo.common.kafka.impl;

import com.demo.demo.common.kafka.EventModel;
import com.demo.demo.common.kafka.consts.EventTypeEnum;
import com.demo.demo.common.kafka.consts.KafkaTopicEnum;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

/**
 * Created by liuyang on 2018/11/9
 */
@Component
public class BroadcastTestModel implements EventModel<String> {

    private final String TOPIC = KafkaTopicEnum.TEST.topic();

    @Override
    public void handle(String str, ConsumerRecord<String, String> record) {
        System.out.println();
        System.out.println("topic ------> " + record.topic());
        System.out.println("offset ------> " + record.offset());
        System.out.println("success consumes a message : " + str);
    }

    @Override
    public String getTopic() {
        return TOPIC;
    }

    @Override
    public EventTypeEnum getEventType() {
        return EventTypeEnum.STAND_ALONE;
    }

}
