package com.demo.demo.business.demo.dao.cache.impl;

import com.demo.demo.business.demo.dao.cache.UserCache;
import com.demo.demo.business.demo.pojo.entity.UserEntity;
import com.demo.demo.business.demo.service.UserService;
import com.demo.demo.common.kafka.EventModel;
import com.demo.demo.common.kafka.consts.KafkaTopicEnum;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by liuyang on 2018/11/10
 */
@Component
public class UserCacheImpl implements UserCache{

    @Override
    public UserEntity get(Integer id) {
        return null;
    }

    @Override
    public void set(UserEntity userEntity) {

    }

    @Override
    public void remove(Integer id) {

    }
}
