## Start the zookeeper nodes

```bash
confluent-7.6.1/bin/zookeeper-server-start confluent-7.6.1/etc/kafka/zookeeper.properties
```





## start zookeeper shell to check metadadata

```bash
confluent-7.6.1/bin/zookeeper-shell localhost:2181
```

## basic zookeeper-shell commands

```bash
ls /
create /myznode "hello"
get /myznode
set /myznode "world"
get /myznode
delete /myznode
ls /
```

```bash
quit
```
