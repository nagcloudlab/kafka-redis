package com.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
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

        Properties props = new Properties();
        // Client ID
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer-client-1");
        // List of Kafka brokers to connect to
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
        // Serializer class for key
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // Serializer class for value
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // Acknowledgments for message durability
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        // Enable idempotence to avoid message duplication
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        // Retry settings
        props.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 100);
        // Maximum number of in-flight requests per connection
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);
        // How long to wait before sending a batch in milliseconds
        props.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        // Batch size
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // Compression type
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");
        // Buffer memory
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // block thread when buffer is full
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 60000);
        // Custom partitioner
        // props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,
        // CustomPartitioner.class.getName());
        // Max request size
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 10485760);
        // Request timeout
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        // Delivery timeout
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 120000);
        // Metadata max age
        props.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, 300000);
        // Interceptor classes
        //props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, ProducerClientInterceptor.class.getName());

        // Create a KafkaProducer instance
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Send a message to the topic "topic1"
        for (int i = 0; i < 1; i++) {
            String value = "Hey Kafka!".repeat(100); // 1kb message
            String key = transactionTypes.get(i % 3);
            ProducerRecord<String, String> record = new ProducerRecord<>("topic1", 0, key, value);
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
