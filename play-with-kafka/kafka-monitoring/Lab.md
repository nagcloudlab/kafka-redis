
# Kafka Monitoring using jmx_prometheus_exporter, Prometheus and Grafana
------------------------------------------------------------------------



download the jmx_prometheus_exporter latest jar file from the below link

Ref: https://github.com/prometheus/jmx_exporter?tab=readme-ov-file

```bash
curl -O https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/1.0.1/jmx_prometheus_javaagent-1.0.1.jar
```


Create a configuration file for the jmx_prometheus_exporter


<!-- zookeeper -->
```bash
./bin/zookeeper-server-start.sh config/zookeeper.properties
```


update kafka-run-class.sh file to add the below line for KAFKA_JMX_OPTS


```bash
  KAFKA_JMX_OPTS="-Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=localhost -Djava.net.preferIPv4Stack=true -javaagent:/home/nag/kafka-monitoring/jmx_prometheus_javaagent-1.0.1.jar=$JMX_PROMETHEUS_PORT:/home/nag/kafka-monitoring/jmx_exporter_kafka_broker.yml"
```

start the kafka broker-1 with the below command
```bash
export JMX_PORT=9991
export JMX_PROMETHEUS_PORT=7071

./bin/kafka-server-start.sh config/server-1.properties
```

start the kafka broker-2 with the below command
```bash
export JMX_PORT=9992
export JMX_PROMETHEUS_PORT=7072

./bin/kafka-server-start.sh config/server-2.properties
```

start the kafka broker-3 with the below command
```bash
export JMX_PORT=9993
export JMX_PROMETHEUS_PORT=7073

./bin/kafka-server-start.sh config/server-3.properties
```


install the prometheus-server in ubuntu

https://www.notion.so/nagcloudlab/Install-Prometheus-b50e13a36bb34d948b9c1f21e162fbf1

```bash
sudo nano /etc/prometheus/prometheus.yml
```

```properties
  - job_name: 'kafka'
    static_configs:
    - targets: ['localhost:7071', 'localhost:7072', 'localhost:7073']
```

```bash
sudo systemctl restart prometheus
```



install the grafana-server in ubuntu

https://www.notion.so/nagcloudlab/Install-Grafana-cbdb2927eda24d9592bd34b447eb4625

Configure Provisioning and Dashboards
Create the Provisioning Directory:

sh
Copy code
sudo mkdir -p /etc/grafana/provisioning
Create the Dashboards Directory:

sh
Copy code
sudo mkdir -p /var/lib/grafana/dashboards
Copy Your Provisioning Files:
Assuming you have provisioning files in ./grafana/provisioning:

sh
Copy code
sudo cp -r ./grafana/provisioning/* /etc/grafana/provisioning/
Copy Your Dashboards:
Assuming you have dashboard JSON files in ./grafana/dashboards:

sh
Copy code
sudo cp -r ./grafana/dashboards/* /var/lib/grafana/dashboards/


Create dashboard configuration file for kafka

sudo nano /etc/grafana/provisioning/dashboards/kafka.yml
