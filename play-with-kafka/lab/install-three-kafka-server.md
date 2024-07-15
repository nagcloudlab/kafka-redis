## 3 kafka server

```bash
nano confluent-7.6.1/etc/kafka/server-101.properties
```

```properties
broker.id=101
listeners=PLAINTEXT://localhost:9092
advertised.listeners=PLAINTEXT://public-ip:9092
confluent.metadata.server.listeners=http://0.0.0.0:8090
log.dirs=/tmp/kafka-logs1
zookeeper.connect=localhost:2181,localhost:2182,localhost:2183
```

```bash
nano confluent-7.6.1/etc/kafka/server-102.properties
```

```properties
broker.id=102
listeners=PLAINTEXT://localhost:9093
advertised.listeners=PLAINTEXT://localhost:9093
confluent.metadata.server.listeners=http://0.0.0.0:8091
log.dirs=/tmp/kafka-logs2
zookeeper.connect=localhost:2181,localhost:2182,localhost:2183
```

```bash
nano confluent-7.6.1/etc/kafka/server-103.properties
```

```properties
broker.id=103
listeners=PLAINTEXT://localhost:9094
advertised.listeners=PLAINTEXT://localhost:9094
confluent.metadata.server.listeners=http://0.0.0.0:8092
log.dirs=/tmp/kafka-logs3
zookeeper.connect=localhost:2181,localhost:2182,localhost:2183
```

```bash
confluent-7.6.1/bin/kafka-server-start confluent-7.6.1/etc/kafka/server-101.properties
confluent-7.6.1/bin/kafka-server-start confluent-7.6.1/etc/kafka/server-102.properties
confluent-7.6.1/bin/kafka-server-start confluent-7.6.1/etc/kafka/server-103.properties
```
