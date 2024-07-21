shCopy code
# Add elements to a HyperLogLog
PFADD visits "user1" "user2" "user3"

# Add more elements to the same HyperLogLog
PFADD visits "user2" "user3" "user4"

# Get the approximate number of unique elements
PFCOUNT visits
# Output: 4

# Create another HyperLogLog and add elements
PFADD visits2 "user5" "user6" "user7"

# Merge two HyperLogLogs into one
PFMERGE total_visits visits visits2

# Get the approximate number of unique elements from the merged HyperLogLog
PFCOUNT total_visits
# Output: 7

