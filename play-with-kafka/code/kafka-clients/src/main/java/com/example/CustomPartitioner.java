package com.example;

import org.apache.kafka.common.Cluster;

import java.util.Map;

public class CustomPartitioner implements org.apache.kafka.clients.producer.Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (key.toString().contains("upi")) {
            return 0;
        }
        if (key.toString().contains("neft")) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
