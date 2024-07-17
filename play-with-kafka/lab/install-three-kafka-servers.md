## 3 kafka server

```bash
nano confluent-7.6.1/etc/kafka/server-101.properties
```

```properties
broker.id=101
listeners=PLAINTEXT://localhost:9092
advertised.listeners=PLAINTEXT://localhost:9092
log.dirs=/tmp/kafka-logs1
zookeeper.connect=localhost:2181
confluent.metadata.server.listeners=http://localhost:8090
confluent.metadata.server.advertised.listeners=http://localhost:8090
```

```bash
nano confluent-7.6.1/etc/kafka/server-102.properties
```

```properties
broker.id=102
listeners=PLAINTEXT://localhost:9093
advertised.listeners=PLAINTEXT://localhost:9093
log.dirs=/tmp/kafka-logs2
zookeeper.connect=localhost:2181
confluent.metadata.server.listeners=http://localhost:8090
confluent.metadata.server.advertised.listeners=http://localhost:8090
```

```bash
nano confluent-7.6.1/etc/kafka/server-103.properties
```

```properties
broker.id=103
listeners=PLAINTEXT://localhost:9094
advertised.listeners=PLAINTEXT://localhost:9094
log.dirs=/tmp/kafka-logs3
zookeeper.connect=localhost:2181
confluent.metadata.server.listeners=http://localhost:8090
confluent.metadata.server.advertised.listeners=http://localhost:8090
```


start the kafka servers

```bash
confluent-7.6.1/bin/kafka-server-start confluent-7.6.1/etc/kafka/server-101.properties
confluent-7.6.1/bin/kafka-server-start confluent-7.6.1/etc/kafka/server-102.properties
confluent-7.6.1/bin/kafka-server-start confluent-7.6.1/etc/kafka/server-103.properties
```
