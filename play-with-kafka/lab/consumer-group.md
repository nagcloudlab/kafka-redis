## list kafka consumer groups

```bash
confluent-7.6.1/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --list
```

## describe kafka consumer group

```bash
confluent-7.6.1/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group my-group
```

## reset kafka consumer group

```bash
confluent-7.6.1/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --group my-group --reset-offsets --to-earliest --execute --topic my-topic
```

## reset kafka consumer group to specific offset

```bash
confluent-7.6.1/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --group my-group --reset-offsets --to-offset 0 --execute --topic my-topic
```

## reset kafka consumer group to specific timestamp

```bash
confluent-7.6.1/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --group my-group --reset-offsets --to-datetime 2021-01-01T00:00:00.000 --execute --topic my-topic
```

## reset kafka consumer group to latest

```bash
confluent-7.6.1/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --group my-group --reset-offsets --to-latest --execute --topic my-topic
```

## delete kafka consumer group

```bash
confluent-7.6.1/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --group my-group --delete
```
