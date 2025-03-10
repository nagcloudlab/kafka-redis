## Step 1

To create a cluster, we need to spin up a few empty Redis instances and configure them to run in cluster mode.

Here’s a minimal configuration file for Redis Cluster:

```bash
# redis.conf file
port 7000
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
```

On the first line we specify the port on which the server should run, then we state that we want the server to run in cluster mode, with the cluster-enabled yes directive. cluster-config-file defines the name of the file where the configuration for this node is stored, in case of a server restart. Finally, cluster-node-timeout is the number of milliseconds a node must be unreachable for it to be considered in failure state.

## Step 2

Let’s create a cluster on your localhost with three primary shards and three replicas (remember, in production always use two replicas to protect against a split-brain situation). We’ll need to bring up six Redis processes and create a redis.conf file for each of them, specifying their port and the rest of the configuration directives above.

First, create six directories:

```bash
$ mkdir 7000 7001 7002 7003 7004 7005
```

## Step 3

Then create the minimal configuration redis.conf file from above in each one of them, making sure you change the port directive to match the directory name. You should end up with the following directory structure:

```bash
- 7000
  - redis.conf
- 7001
  - redis.conf
- 7002
  - redis.conf
- 7003
  - redis.conf
- 7004
  - redis.conf
- 7005
  - redis.conf
```

## Step 4

Open six terminal tabs and start the servers by going into each one of the directories and starting a Redis instance:

```bash
# Terminal tab 1
$ cd 7000
$ /path/to/redis-server ./redis.conf

# Terminal tab 2
$ cd 7001
$ /path/to/redis-server ./redis.conf

... and so on.
```

## Step 5

Now that you have six empty Redis servers running, you can join them in a cluster:

```bash
$ redis-cli --cluster create    127.0.0.1:7000 127.0.0.1:7001 \\
127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 \\
--cluster-replicas 1
```

Here we list the ports and IP addresses of all six servers and use the `CREATE` command to instruct Redis to join them in a cluster, creating one replica for each primary. Redis-cli will propose a configuration; accept it by typing yes. The cluster will be configured and joined, which means, instances will be bootstrapped into talking with each other.

Finally, you should see a message saying:

```bash
[OK] All 16384 slots covered
```

This means that there is at least a master instance serving each of the 16384 slots available.

## Step 6

Let’s add a new shard to the cluster, which is something you might do when you need to scale.

First, as before, we need to start two new empty Redis instances (primary and its replica) in cluster mode. We create new directories `7006` and `7007` and in them we copy the same `redis.conf` file we used before, making sure we change the port directive in them to the appropriate port (`7006` and `7007`).

```bash
$ mkdir 7006 7007
$ cp 7000/redis.conf 7006/redis.conf
$ cp 7000/redis.conf 7007/redis.conf
```

Update the port numbers in the files `./7006/redis.conf` and `./7007/redis.conf` to `7006` and `7007`, respectively.

## Step 7

Let’s start the Redis instances:

```bash
# Terminal tab 7
$ cd 7006
$ redis-server ./redis.conf

# Terminal tab 8
$ cd 7007
$ redis-server ./redis.conf
```

## Step 8

In the next step we join the new primary shard to the cluster with the add-node command. The first parameter is the address of the new shard, and the second parameter is the address of any of the current shards in the cluster.

```bash
$ redis-cli --cluster add-node 127.0.0.1:7006 127.0.0.1:7000
```

**Note: The Redis commands use the term “Nodes” for what we call “Shards” in this training, so a command named “add-node” would mean “add a shard”.**

## Step 9

Finally we need to join the new replica shard, with the same add-node command, and a few extra arguments indicating the shard is joining as a replica and what will be its primary shard. If we don’t specify a primary shard Redis will assign one itself.

We can find the IDs of our shards by running the cluster nodes command on any of the shards:

```bash
$ redis-cli -p 7000 cluster nodes
46a768cfeadb9d2aee91ddd882433a1798f53271 127.0.0.1:7006@17006 master - 0 1616754504000 0 connected
1f2bc068c7ccc9e408161bd51b695a9a47b890b2 127.0.0.1:7003@17003 slave a138f48fe038b93ea2e186e7a5962fb1fa6e34fa 0 1616754504551 3 connected
5b4e4be56158cf6103ffa3035024a8d820337973 127.0.0.1:7001@17001 master - 0 1616754505584 2 connected 5461-10922
a138f48fe038b93ea2e186e7a5962fb1fa6e34fa 127.0.0.1:7002@17002 master - 0 1616754505000 3 connected 10923-16383
71e078dab649166dcbbcec51520742bc7a5c1992 127.0.0.1:7005@17005 slave 5b4e4be56158cf6103ffa3035024a8d820337973 0 1616754505584 2 connected
f224ecabedf39d1fffb34fb6c1683f8252f3b7dc 127.0.0.1:7000@17000 myself,master - 0 1616754502000 1 connected 0-5460
04d71d5eb200353713da475c5c4f0a4253295aa4 127.0.0.1:7004@17004 slave f224ecabedf39d1fffb34fb6c1683f8252f3b7dc 0 1616754505896 1 connected
```

The port of the primary shard we added in the last step was `7006`, and we can see it on the first line. It’s id is `46a768cfeadb9d2aee91ddd882433a1798f53271`.

The resulting command is:

```bash
$ redis-cli -p 8000 --cluster add-node 127.0.0.1:8007 127.0.0.1:8000 --cluster-slave --cluster-master-id d8cfe273a313148e8311d3962e55e524cb4681af
```

The flag `cluster-slave` indicates that the shard should join as a replica and `--cluster-master-id 46a768cfeadb9d2aee91ddd882433a1798f53271` specifies which primary shard it should replicate.

## Step 10

Now our cluster has eight shards (four primary and four replica), but if we run the cluster slots command we’ll see that the newly added shards don’t host any hash slots, and thus - data. Let’s assign some hash slots to them:

```bash
$ redis-cli  -p 7000  --cluster reshard 127.0.0.1:7000
```

We use the command reshard and the address of any shard in the cluster as an argument here. In the next step we’ll be able to choose the shards we’ll be moving slots from and to.

The first question you’ll get is about the number of slots you want to move. If we have 16384 slots in total, and four primary shards, let’s get a quarter of all shards, so the data is distributed equally. 16384 ÷ 4 is 4096, so let’s use that number.

The next question is about the receiving shard id; the ID of the primary shard we want to move the data to, which we learned how to get in the previous step, with the cluster nodes command.

Finally, we need to enter the IDs of the shards we want to copy data from. Alternatively, we can type “all” and the shard will move a number of hash slots from all available primary shards.

```bash
$ redis-cli -p 7000 --cluster reshard 127.0.0.1:7000
....
....
....

How many slots do you want to move (from 1 to 16384)? 4096
What is the receiving node ID? 46a768cfeadb9d2aee91ddd882433a1798f53271
Please enter all the source node IDs.
  Type 'all' to use all the nodes as source nodes for the hash slots.
  Type 'done' once you entered all the source nodes IDs.
Source node #1: all

Ready to move 4096 slots.
  Source nodes:
	M: f224ecabedf39d1fffb34fb6c1683f8252f3b7dc 127.0.0.1:7000
   	slots:\[0-5460\] (5461 slots) master
   	1 additional replica(s)
	M: 5b4e4be56158cf6103ffa3035024a8d820337973 127.0.0.1:7001
   	slots:\[5461-10922\] (5462 slots) master
   	1 additional replica(s)
	M: a138f48fe038b93ea2e186e7a5962fb1fa6e34fa 127.0.0.1:7002
   	slots:\[10923-16383\] (5461 slots) master
   	1 additional replica(s)
  Destination node:
	M: 46a768cfeadb9d2aee91ddd882433a1798f53271 127.0.0.1:7006
   	slots: (0 slots) master
   	1 additional replica(s)
  Resharding plan:
	Moving slot 5461 from 5b4e4be56158cf6103ffa3035024a8d820337973
	Moving slot 5462 from 5b4e4be56158cf6103ffa3035024a8d820337973

Do you want to proceed with the proposed reshard plan (yes/no)?
Moving slot 5461 from 127.0.0.1:7001 to 127.0.0.1:7006:
Moving slot 5462 from 127.0.0.1:7001 to 127.0.0.1:7006:
Moving slot 5463 from 127.0.0.1:7001 to 127.0.0.1:7006:
....
....
....
```

Once the command finishes we can run the cluster slots command again and we’ll see that our new primary and replica shards have been assigned some hash slots:

```bash
$ redis-cli -p 7000 cluster slots
```
