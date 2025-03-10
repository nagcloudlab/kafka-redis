package com.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TransactionalProducerClient {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "4.247.148.242:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "prod-1");
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        try {
            // Initialize transactions
            producer.initTransactions(); // Finding Transaction Coordinator

            // Start a transaction
            producer.beginTransaction();

            try {
                // Send messages within a transaction
                producer.send(new ProducerRecord<>("txn-topic", "key1", "value1"));
                producer.send(new ProducerRecord<>("txn-topic", "key2", "value2"));

                TimeUnit.SECONDS.sleep(5); // Simulate a delay
                if (true)
                    throw new RuntimeException("Simulated error"); // Simulate an error
                //
                // Commit the transaction
                producer.commitTransaction();

            } catch (Exception e) {
                // Abort the transaction in case of an error
                producer.abortTransaction();
                e.printStackTrace();
            }
        } finally {
            producer.close();
        }
    }
}
