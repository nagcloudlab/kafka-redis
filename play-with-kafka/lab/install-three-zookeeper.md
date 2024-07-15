## Create the data directories for each zookeeper node

```bash
mkdir -p /tmp/zookeeper1 /tmp/zookeeper2 /tmp/zookeeper3
```

## Create 'myid' files for each zookeeper node

```bash
echo 1 > /tmp/zookeeper1/myid
echo 2 > /tmp/zookeeper2/myid
echo 3 > /tmp/zookeeper3/myid
```

## Edit the zookeeper properties

```bash
nano confluent-7.6.1/etc/kafka/zookeeper1.properties
```

```properties
dataDir=/tmp/zookeeper1
clientPort=2181
maxClientCnxns=0
initLimit=5
syncLimit=2
server.1=localhost:2888:3888
server.2=localhost:2889:3889
server.3=localhost:2890:3890
```

```bash
nano confluent-7.6.1/etc/kafka/zookeeper2.properties
```

```properties
dataDir=/tmp/zookeeper2
clientPort=2182
maxClientCnxns=0
initLimit=5
syncLimit=2
server.1=localhost:2888:3888
server.2=localhost:2889:3889
server.3=localhost:2890:3890
```

```bash
nano confluent-7.6.1/etc/kafka/zookeeper3.properties
```

```properties
dataDir=/tmp/zookeeper3
clientPort=2183
maxClientCnxns=0
initLimit=5
syncLimit=2
server.1=localhost:2888:3888
server.2=localhost:2889:3889
server.3=localhost:2890:3890
```

## Start the zookeeper nodes

```bash
confluent-7.6.1/bin/zookeeper-server-start confluent-7.6.1/etc/kafka/zookeeper.properties

confluent-7.6.1/bin/zookeeper-server-start confluent-7.6.1/etc/kafka/zookeeper1.properties
confluent-7.6.1/bin/zookeeper-server-start confluent-7.6.1/etc/kafka/zookeeper2.properties
confluent-7.6.1/bin/zookeeper-server-start confluent-7.6.1/etc/kafka/zookeeper3.properties
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
