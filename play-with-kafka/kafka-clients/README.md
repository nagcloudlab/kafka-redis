to run producer

```bash
mvn compile exec:java -D exec.mainClass="com.example.ProducerClient"
```

to run consumer

```bash
mvn compile exec:java -D exec.mainClass="com.example.ConsumerClient"
```


to run transactional producer
```bash
mvn compile exec:java -D exec.mainClass="com.example.TransactionalProducerClient"
```

to run transactional consumer

```bash
mvn compile exec:java -D exec.mainClass="com.example.TransactionalConsumerClient"
```

to run StreamsClient
```bash
mvn compile exec:java -D exec.mainClass="com.example.StreamsClient"
```