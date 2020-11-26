

### Infra

#

#### Description
* Contains the necessary infrastructure (docker-images) that our platform is using, dependencies:
    * Apache Kafka (1 Zookeeper with 3 Kafka Brokers)
    * Cassandra (3 nodes)
    
#### Prerequisites

1) Docker installed on your machine

2) Docker compose installed on your machine
    
    
#### Running/Setting up Apache Kafka (1 Zookeeper with 3 Kafka Brokers)

* Setup docker containers for Zookeeper and Kafka: `docker-compose -f docker-compose-kafka-infra.yml up`

* Enter in some kafka-broker docker container: ` docker exec -it <some_broker_id> bash`

* List topics (to make sure it is deleted from `docker volume prune`): `root@kafka1:/bin# kafka-topics --list --zookeeper zookeeper:2181`

* Create `log-events` topic: `root@kafka1:/bin# kafka-topics --create --zookeeper zookeeper:2181 --replication-factor 3 --partitions 16 --topic log-events`

* Check if all ok: `root@kafka1:/bin# kafka-topics --bootstrap-server=localhost:9092 --describe --topic log-events`

    * Output:
        ```text
        Topic: log-events       PartitionCount: 16      ReplicationFactor: 3    Configs: 
                Topic: log-events       Partition: 0    Leader: 102     Replicas: 102,1,3       Isr: 102,1,3    Offline: 
                Topic: log-events       Partition: 1    Leader: 1       Replicas: 1,3,102       Isr: 1,3,102    Offline: 
                Topic: log-events       Partition: 2    Leader: 3       Replicas: 3,102,1       Isr: 3,102,1    Offline: 
                Topic: log-events       Partition: 3    Leader: 102     Replicas: 102,3,1       Isr: 102,3,1    Offline: 
                Topic: log-events       Partition: 4    Leader: 1       Replicas: 1,102,3       Isr: 1,102,3    Offline: 
                Topic: log-events       Partition: 5    Leader: 3       Replicas: 3,1,102       Isr: 3,1,102    Offline: 
                Topic: log-events       Partition: 6    Leader: 102     Replicas: 102,1,3       Isr: 102,1,3    Offline: 
                Topic: log-events       Partition: 7    Leader: 1       Replicas: 1,3,102       Isr: 1,3,102    Offline: 
                Topic: log-events       Partition: 8    Leader: 3       Replicas: 3,102,1       Isr: 3,102,1    Offline: 
                Topic: log-events       Partition: 9    Leader: 102     Replicas: 102,3,1       Isr: 102,3,1    Offline: 
                Topic: log-events       Partition: 10   Leader: 1       Replicas: 1,102,3       Isr: 1,102,3    Offline: 
                Topic: log-events       Partition: 11   Leader: 3       Replicas: 3,1,102       Isr: 3,1,102    Offline: 
                Topic: log-events       Partition: 12   Leader: 102     Replicas: 102,1,3       Isr: 102,1,3    Offline: 
                Topic: log-events       Partition: 13   Leader: 1       Replicas: 1,3,102       Isr: 1,3,102    Offline: 
                Topic: log-events       Partition: 14   Leader: 3       Replicas: 3,102,1       Isr: 3,102,1    Offline: 
                Topic: log-events       Partition: 15   Leader: 102     Replicas: 102,3,1       Isr: 102,3,1    Offline: 

        ```

* Optional, after some time: `docker compose ps`
* Optional, after some time: `docker-compose down`



#### Running up Cassandra (3 nodes)

* Execute: ` docker-compose -f docker-compose-cassandra-infra.yml up`
