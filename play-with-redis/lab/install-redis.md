

# Install Redis

sudo apt update
sudo apt install redis-server -y
sudo systemctl status redis
sudo nano /etc/redis/redis.conf
sudo systemctl restart redis
sudo systemctl enable redis
redis-cli
ping


