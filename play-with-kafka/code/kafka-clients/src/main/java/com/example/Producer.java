package com.example;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {
    public static void main(String[] args) {


        Properties props = new Properties();

        // Client ID
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer-client-1");
        // List of Kafka brokers to connect to
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "52.140.78.183:9092");
        // Serializer class for key
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // Serializer class for value
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        org.apache.kafka.clients.producer.Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);

        // Send a message
        producer.send(new org.apache.kafka.clients.producer.ProducerRecord<>("topic3", "Hello, World!"));

        // Close the producer
        producer.close();


    }
}
