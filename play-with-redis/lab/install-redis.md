# Install Redis

sudo apt update
sudo apt install redis-server -y
sudo systemctl status redis
sudo systemctl stop redis

sudo nano /etc/redis/redis.conf

redis-server /etc/redis/redis.conf
redis-cli
