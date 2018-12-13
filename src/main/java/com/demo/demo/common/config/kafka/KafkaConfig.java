package com.demo.demo.common.config.kafka;

import com.demo.demo.common.config.BootstrapConfig;
import com.demo.demo.common.config.CommonConfig;

import java.util.Properties;

/**
 * Created by liuyang on 2018/11/7
 */
public class KafkaConfig {

    public static final KafkaConfig INSTANCE = new KafkaConfig();

    private static final Properties producerProperties = new Properties();
    private static final Properties consumerProperties = new Properties();

    static {
        String serverIp = BootstrapConfig.get("server.ip");
        String port = BootstrapConfig.get("kafka.port");
        String address = serverIp + ":" + port;
        producerProperties.put("bootstrap.servers", address);
        producerProperties.put("acks", "all");
        producerProperties.put("retries", 0);
        producerProperties.put("batch.size", 16384);
        producerProperties.put("linger.ms", 1);
        producerProperties.put("buffer.memory", 33554432);
        producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        consumerProperties.put("bootstrap.servers", address);
        consumerProperties.put("enable.auto.commit", "true");
        consumerProperties.put("auto.commit.interval.ms", "1000");
        consumerProperties.put("auto.offset.reset", "earliest");
        consumerProperties.put("session.timeout.ms", "30000");
        consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    private KafkaConfig(){}

    public Properties getProducerProperties() {
        return producerProperties;
    }

    public Properties getConsumerProperties() {
        return consumerProperties;
    }
}
