import sys
import time
import redis
from redis.sentinel import Sentinel

# Determine startup nodes based on command-line arguments
if len(sys.argv) != 3:
    sentinel_hosts = [("127.0.0.1", 26379)]
else:
    sentinel_hosts = [(sys.argv[1], int(sys.argv[2]))]

# Connect to the Sentinel
sentinel = Sentinel(sentinel_hosts, socket_timeout=0.1)

# Discover the master and replica using Sentinel
master = sentinel.master_for('mymaster', socket_timeout=0.1, decode_responses=True)
replica = sentinel.slave_for('mymaster', socket_timeout=0.1, decode_responses=True)

# Initialize the last key value
last = None

while last is None:
    try:
        last = master.get("__last__")
        if last is None:
            last = 0
    except Exception as e:
        print(f"error {str(e)}")
        time.sleep(1)

# Write to master and read from replica
for x in range(int(last) + 1, 1000001):  # Adjust the range for demonstration purposes
    try:
        master.set(f"foo{x}", x)
        print(f"Master set: foo{x} = {x}")

        # Allow some time for the replica to replicate the data
        time.sleep(0.1)

        replica_value = replica.get(f"foo{x}")
        print(f"Replica read: foo{x} = {replica_value}")

        master.set("__last__", x)
    except Exception as e:
        print(f"error {str(e)}")
    # Optional: Uncomment the next line to introduce a delay between operations
    # time.sleep(0.1)

# Clean up by removing the keys from the master
for x in range(int(last) + 1):
    master.delete(f"foo{x}")
master.delete("__last__")
