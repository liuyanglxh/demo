package com.demo.demo.common.kafka.consts;

/**
 * Created by liuyang on 2018/11/9
 */
public enum EventTypeEnum {
    EXCLUSIVE,  //只被一个消费者消费
    STAND_ALONE //可以被多个消费者消费
}
