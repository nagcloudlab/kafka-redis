## Start the zookeeper nodes

```bash
confluent-7.6.1/bin/zookeeper-server-start confluent-7.6.1/etc/kafka/zookeeper.properties
```

## start zookeeper shell

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

## stop zookeeper nodes

```bash
confluent-7.6.1/bin/zookeeper-server-stop confluent-7.6.1/etc/kafka/zookeeper1.properties
confluent-7.6.1/bin/zookeeper-server-stop confluent-7.6.1/etc/kafka/zookeeper2.properties
confluent-7.6.1/bin/zookeeper-server-stop confluent-7.6.1/etc/kafka/zookeeper3.properties
```

## cleanup

```bash
rm -rf /tmp/zookeeper1 /tmp/zookeeper2 /tmp/zookeeper3
```

## Conclusion

In this lab, we learned how to install and configure a zookeeper cluster. We also learned how to interact with zookeeper using the zookeeper-shell.
