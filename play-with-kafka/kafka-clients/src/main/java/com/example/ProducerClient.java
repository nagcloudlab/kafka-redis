package com.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

// Custom Partitioner
/*
class CustomPartitioner implements org.apache.kafka.clients.producer.Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (key.toString().contains("key1")) {
            return 0;
        }
        if (key.toString().contains("key2")) {
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
*/

// Interceptor
/*
class ProducerInterceptor implements org.apache.kafka.clients.producer.ProducerInterceptor<String, String> {

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        producerRecord.headers().add("origin", "npci-chennai".getBytes());
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        // ..
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}

 */

public class ProducerClient {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ProducerClient.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Properties props = new Properties();
        // Client ID
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer-client-1");
        // List of Kafka brokers to connect to
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
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
        // props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
        // ProducerClientInterceptor.class.getName());
        // Create a KafkaProducer instance

        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config",
                "org.apache.kafka.common.security.plain.PlainLoginModule required username='admin' password='admin-secret';");

        // props.put("ssl.truststore.location",
        //         "/Users/nag/kafka-redis/play-with-kafka/lab/ssl/kafka.broker-1.truststore.jks");
        // props.put("ssl.truststore.password", "changeme");

        // props.put("ssl.keystore.location",
        //         "/Users/nag/kafka-redis/play-with-kafka/lab/ssl/kafka.client.keystore.jks");
        // props.put("ssl.keystore.password", "changeme");
        // props.put("ssl.key.password", "changeme");



        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        // Send a message to the topic "topic1"
        for (int i = 0; i < 1; i++) {
//            String value = "Hey Kafka!".repeat(100); // 1kb message
            String value = String.valueOf(i);
            ProducerRecord<String, String> record = new ProducerRecord<>("numbers", value);
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
