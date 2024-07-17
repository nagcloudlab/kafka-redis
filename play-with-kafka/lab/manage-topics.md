## Manage Kafka Topics

### Create a Topic
```bash
confluent-7.6.1/bin/kafka-topics --create --topic my-topic --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
```

### List Topics
```bash
confluent-7.6.1/bin/kafka-topics --list --bootstrap-server localhost:9092
```

### Describe a Topic
```bash
confluent-7.6.1/bin/kafka-topics --describe --topic my-topic --bootstrap-server localhost:9092
```

### Delete a Topic
```bash
confluent-7.6.1/bin/kafka-topics --delete --topic my-topic --bootstrap-server localhost:9092
```

### Alter a Topic
```bash
confluent-7.6.1/bin/kafka-topics --alter --topic my-topic --partitions 3 --bootstrap-server localhost:9092
```

### Topic Configuration
```bash
confluent-7.6.1/bin/kafka-configs --describe --entity-type topics --entity-name my-topic --bootstrap-server localhost:9092
```

### Update Topic Configuration
```bash
confluent-7.6.1/bin/kafka-configs --alter --entity-type topics --entity-name my-topic --add-config retention.ms=86400000 --bootstrap-server localhost:9092
```

### Delete Topic Configuration
```bash
confluent-7.6.1/bin/kafka-configs --alter --entity-type topics --entity-name my-topic --delete-config retention.ms --bootstrap-server localhost:9092
```
