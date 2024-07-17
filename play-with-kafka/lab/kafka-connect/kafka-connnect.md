

start kafka connect-cluster in distributed mode with 2 worker nodes

```bash
confluent-7.6.1/bin/connect-distributed confluent-7.6.1/etc/kafka/connect-distributed-worker-1.properties
```

```bash
confluent-7.6.1/bin/connect-distributed confluent-7.6.1/etc/kafka/connect-distributed-worker-2.properties
```

-------------------------------------------------------------------------------------------------


get available/installed connector-plugins

```bash
curl http://localhost:8083/connector-plugins | jq
```

get deployed connectors

```bash
curl http://localhost:8083/connectors | jq
```

-------------------------------------------------------------------------------------------------


deploy file-source connector

```bash
curl -X POST -H "Content-Type: application/json" -d @connnectors/file-source-connector.json http://localhost:8083/connectors | jq
```

list deployed connectors

```bash
curl http://localhost:8083/connectors | jq
```

get connector status

```bash
curl http://localhost:8083/connectors/file-source-connector/status | jq
```


get connector config

```bash
curl http://localhost:8083/connectors/file-source-connector | jq
```

get connector tasks

```bash
curl http://localhost:8083/connectors/file-source-connector/tasks | jq
```


get connector task status

```bash
curl http://localhost:8083/connectors/file-source-connector/tasks/0/status | jq
```

pause connector

```bash
curl -X PUT http://localhost:8083/connectors/file-source-connector/pause | jq
```

resume connector

```bash
curl -X PUT http://localhost:8083/connectors/file-source-connector/resume | jq
```

restart connector

```bash
curl -X POST http://localhost:8083/connectors/file-source-connector/restart | jq
```

delete connector

```bash
curl -X DELETE http://localhost:8083/connectors/file-source-connector | jq
```

-------------------------------------------------------------------------------------------------

deploy file-sink connector

```bash
curl -X POST -H "Content-Type: application/json" -d @connnectors/file-sink-connector.json http://localhost:8083/connectors | jq
```

list deployed connectors

```bash
curl http://localhost:8083/connectors | jq
```
-------------------------------------------------------------------------------------------------