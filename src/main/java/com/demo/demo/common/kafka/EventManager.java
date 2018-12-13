package com.demo.demo.common.kafka;

import com.demo.demo.common.kafka.consts.EventTypeEnum;

/**
 * Created by liuyang on 2018/11/9
 */
public interface EventManager {

    /**
     * 异步发送消息
     */
    void sendAsync(String topic, Object target, EventTypeEnum eventType);

    /**
     * 同步发送消息
     */
    boolean sendSync(String topic, Object target, EventTypeEnum eventType);
}
