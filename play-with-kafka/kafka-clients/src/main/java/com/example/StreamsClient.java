package com.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class StreamsClient {
    public static void main(String[] args) {

        // the builder is used to construct the topology
        StreamsBuilder builder = new StreamsBuilder();

        // read from the source topic, "users"
        KStream<Void, String> stream = builder.stream("numbers");

        // for each record that appears in the source topic,
        // print the value
//        stream.foreach(
//                (key, value) -> {
//                    System.out.println("-> " + value);
//                });

        // write the records to the sink topic, "all-numbers"
        // stream.to("all-numbers");


        //----------------------------------------------
        //stateless operations
        //----------------------------------------------

        //----------------------------------------------
        // 1. filter
        //----------------------------------------------

//         stream.filter((key, value) -> Integer.parseInt(value) % 2 != 0)
//                    .to("odd-numbers");

//         stream.filter((key, value) -> Integer.parseInt(value) % 2 == 0)
//                    .to("even-numbers");

        //----------------------------------------------
        // 2. map
        //----------------------------------------------
//        stream.mapValues(value -> Integer.toString(Integer.parseInt(value) * Integer.parseInt(value)))
//                .to("squared-numbers");

        //----------------------------------------------
        // 3. flatMap
        //----------------------------------------------
//        stream.flatMapValues(value -> Arrays.asList(Integer.toString(Integer.parseInt(value) - 1), Integer.toString(Integer.parseInt(value) + 1)))
//                .to("neighbour-numbers");


        //----------------------------------------------
        // 4. selectKey
        //----------------------------------------------

//        stream.selectKey((key, value) -> value)
//                .to("numbers-by-value");

        //----------------------------------------------
        // 5. branch
        //----------------------------------------------
//
//         KStream<Void, String>[] branches = stream.branch(
//                (key, value) -> Integer.parseInt(value) % 2 == 0,
//                (key, value) -> Integer.parseInt(value) % 2 != 0);
//
//
//        branches[0].to("even-numbers");
//        branches[1].to("odd-numbers");

        //----------------------------------------------
        // 6. peek
        //----------------------------------------------

//        stream.peek((key, value) -> System.out.println("peek: " + value))
//                .to("peeked-numbers");

        //----------------------------------------------
        // 7. process
        //----------------------------------------------

//        stream.process(() -> new NumberProcessor());

        //----------------------------------------------



        //----------------------------------------------
        //stateless operations
        //----------------------------------------------

        //----------------------------------------------
        // 1. groupBy
        //----------------------------------------------

//        calculate odd and even numbers count and send result to numbers-count topic
        // materialize the result to a topic named "numbers-count"
        // as string key and string value

//        stream.groupBy((key, value) -> {
//            if (Integer.parseInt(value) % 2 == 0) {
//                return "even";
//            } else {
//                return "odd";
//            }
//        }).count(Materialized.as("numbers-count"))
//                .toStream()
//                .map((key, value) -> {
//                    return new org.apache.kafka.streams.KeyValue<>("count", key + " : " + value);
//                })
//                .to("numbers-count", Produced.with(Serdes.String(), Serdes.String()));

        //----------------------------------------------
        // set the required properties for running Kafka Streams
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev1");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.EXACTLY_ONCE_V2, true);
        config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 3);


        // build the topology and start streaming
        Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, config);
        streams.start();

        // close Kafka Streams when the JVM shuts down (e.g. SIGTERM)
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }
}
