import sys
import time
import redis

# Determine startup nodes based on command-line arguments
master_host = "127.0.0.1"
master_port = 7000
replica_host = "127.0.0.1"
replica_port = 7001

# Connect to the master and replica Redis instances
master = redis.StrictRedis(host=master_host, port=master_port, decode_responses=True)
replica = redis.StrictRedis(host=replica_host, port=replica_port, decode_responses=True)

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
# for key, _ in generate_messages(int(last) + 1):
#     master.delete(key)
