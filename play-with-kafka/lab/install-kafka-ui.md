# install kafka-ui

```bash
mkdir kafka-ui
cd kafka-ui
curl -L https://github.com/provectus/kafka-ui/releases/download/v0.7.2/kafka-ui-api-v0.7.2.jar --output kafka-ui-api-v0.7.2.jar
```

```bash
nano application.yml
```

```yaml
kafka:
  clusters:
    - name: local
      bootstrapServers: localhost:9092
      kafkaConnect:
      - name: worker1
        address: http://localhost:8083
```

Run kafka-ui

```bash
java -Dspring.config.additional-location=application.yml --add-opens java.rmi/javax.rmi.ssl=ALL-UNNAMED -jar kafka-ui-api-v0.7.2.jar
```
