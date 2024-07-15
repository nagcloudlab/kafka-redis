# install kafka-ui

```bash
mkdir kafka-ui
curl -L https://github.com/provectus/kafka-ui/releases/download/v0.7.2/kafka-ui-api-v0.7.2.jar --output kafka-ui/kafka-ui-api-v0.7.2.jar
```

```bash
nano kafka-ui/application.yml
```

```yaml
kafka:
  clusters:
    - name: local
      bootstrapServers: localhost:9092,localhost:9093,localhost:9094
```

Run kafka-ui

```bash
cd kafka-ui
java -Dspring.config.additional-location=application.yml --add-opens java.rmi/javax.rmi.ssl=ALL-UNNAMED -jar kafka-ui-api-v0.7.2.jar
```
