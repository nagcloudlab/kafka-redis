package com.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Producer {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(Producer.class);

    private static List<String> transactionTypes = List.of("upi","neft","rtgs");
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // Set the properties for the KafkaProducer
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "4.247.148.242:9092,4.247.148.242:9093,4.247.148.242:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.example.CustomPartitioner");

        // Create a KafkaProducer instance
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // Send a message to the topic "topic1"
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String value = "Hey Kafka!".repeat(100); // 1kb message
            String key = transactionTypes.get(i % 3);
            ProducerRecord<String, String> record = new ProducerRecord<>("topic2", 0, key, value);
            producer.send(record, (recordMetadata, e) -> {
                if (e == null) {
                    logger.info("Received new metadata \nTopic: {}\nKey: {}\nPartition: {}\nOffset: {}\nTimestamp: {}",
                            recordMetadata.topic(),
                            null,
                            recordMetadata.partition(),
                            recordMetadata.offset(),
                            recordMetadata.timestamp());
                } else {
                    logger.error("Error while producing", e);
                }
            });

            TimeUnit.MILLISECONDS.sleep(1);
        }

        // Close the producer
        producer.close();

    }
}
