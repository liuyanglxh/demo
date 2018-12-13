package com.demo.demo.common.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;

import java.util.List;
import java.util.Map;

public class CustomerPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);

        if (key == null || !(key instanceof String )){
            throw new InvalidRecordException("key must be a String");
        }

        if (topic.equals("hello")){
            return 1;
        }
        //其他分区策略
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
