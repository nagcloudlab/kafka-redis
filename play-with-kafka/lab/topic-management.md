## Manage Kafka Topics

### Create a Topic

To create a topic, you can use the `kafka-topics.sh` script. The following command creates a topic named `my-topic` with a single partition and a replication factor of 1:

```bash
confluent-7.6.1/bin/kafka-topics --create --topic my-topic --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
```

### List Topics

To list all topics, you can use the `kafka-topics.sh` script. The following command lists all topics:

```bash
confluent-7.6.1/bin/kafka-topics --list --bootstrap-server localhost:9092
```

### Describe a Topic

To describe a topic, you can use the `kafka-topics.sh` script. The following command describes the `my-topic` topic:

```bash
confluent-7.6.1/bin/kafka-topics --describe --topic my-topic --bootstrap-server localhost:9092
```

### Delete a Topic

To delete a topic, you can use the `kafka-topics.sh` script. The following command deletes the `my-topic` topic:

```bash
confluent-7.6.1/bin/kafka-topics --delete --topic my-topic --bootstrap-server localhost:9092
```

### Alter a Topic

To alter a topic, you can use the `kafka-topics.sh` script. The following command alters the `my-topic` topic to have 3 partitions:

```bash
confluent-7.6.1/bin/kafka-topics --alter --topic my-topic --partitions 3 --bootstrap-server localhost:9092
```

### Topic Configuration

To get the configuration of a topic, you can use the `kafka-configs.sh` script. The following command gets the configuration of the `my-topic` topic:

```bash
confluent-7.6.1/bin/kafka-configs --describe --entity-type topics --entity-name my-topic --bootstrap-server localhost:9092
```

### Update Topic Configuration

To update the configuration of a topic, you can use the `kafka-configs.sh` script. The following command updates the configuration of the `my-topic` topic to have a retention period of 1 day:

```bash
confluent-7.6.1/bin/kafka-configs --alter --entity-type topics --entity-name my-topic --add-config retention.ms=86400000 --bootstrap-server localhost:9092
```

### Delete Topic Configuration

To delete the configuration of a topic, you can use the `kafka-configs.sh` script. The following command deletes the configuration of the `my-topic` topic:

```bash
confluent-7.6.1/bin/kafka-configs --alter --entity-type topics --entity-name my-topic --delete-config retention.ms --bootstrap-server localhost:9092
```
