package com.example;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Consumer {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "g2");
//        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "instance-1"); // static group instance id
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "4.247.148.242:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class.getName());

        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 52428800);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 1048576);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(java.util.Collections.singletonList("topic1"));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Closing consumer...");
            consumer.wakeup();
        }));

        try {
            while (true) {
//                logger.info("Polling...");
                Map<TopicPartition, OffsetAndMetadata> currentProcessedOffsets = new HashMap<>(); // external cache
                ConsumerRecords<String, String> records = consumer.poll(java.time.Duration.ofMillis(100));
//                logger.info("Polled " + records.count() + " records");
                records.forEach(record -> {
                    logger.info("Topic " + record.topic());
                    logger.info("Record Partition " + record.partition());
                    logger.info("Record Offset " + record.offset());
                    logger.info("Record Key " + record.key());
                    logger.info("Record Value " + record.value());
                    currentProcessedOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
                });
                consumer.commitSync(currentProcessedOffsets);

            }
        } catch (WakeupException e) {
            logger.info("Wakeup called");
        } finally {
            consumer.close(); // close consumer
        }

    }
}
