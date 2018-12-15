package com.demo.demo.business.demo.event;

import com.demo.demo.business.demo.pojo.entity.UserEntity;
import com.demo.demo.common.kafka.EventModel;
import com.demo.demo.common.kafka.consts.KafkaTopicEnum;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Created by liuyang on 2018/11/14
 */
public class UserEventModel implements EventModel<UserEntity> {

    @Override
    public void handle(UserEntity userEntity, ConsumerRecord<String, String> record) {
        System.out.println("user : " + userEntity.getId() + " has been changed");
    }

    @Override
    public KafkaTopicEnum getTopic() {
        return KafkaTopicEnum.USER_CHANGE;
    }

}
