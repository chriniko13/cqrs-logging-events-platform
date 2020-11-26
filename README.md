
# Logging System


##### Assignee: Nikolaos Christidis (nick.christidis@yahoo.com)


#### Prerequisites

* Maven
* JDK 11
* Apache Thrift

#### Components of the Platform

1. Module [thrift-client](thrift-client/README.md) is the program that we use to send the logging
event to thrift-server.

2. Module [thrift-server](thrift-server/README.md) implements the other end of the thrift API and
sends logging events to Kafka.

3. Module [kafka-consumer](kafka-consumer/README.md) reads events from Kafka and writes/materializes them to
Cassandra.

4. Module [clients-simulator](clients-simulator/README.md) which produces traffic via using `thrift-client` library to send log events/messages.

5. Module [infra](infra/README.md) which provides the necessary docker images in order to setup the infrastructure needed (Kafka, Cassandra)

```text

    Producer side:

    application_uses { [thrift-client] } ---sends log message---> [thrift-server] ---sends log message---> [kafka]



    Consumer side:

    daemon_or_scheduler_or_something_similar { [kafka-consumer] }
                                                     <--- reads log message from --- [kafka]
                                                     --- materializes log message to ---> [cassandra]   
```


#### Important Notes:

* (Selected by default) In order to use [LoggingEventServiceAsyncClient](thrift-client/src/main/scala/com/chriniko/pollfish/thrift/client/LoggingEventServiceAsyncClient.scala) you should use
the following server flavour: [THREADED_SELECTOR_SERVER](thrift-server/src/main/java/com/chriniko/pollfish/thrift/server/infra/ServerFlavour.java) for the thrift-server.


#### Logging Event Definition

* [logging_event_service.thrift](thrift-server/src/main/java/logging_event_service.thrift)



#### Installing Thrift - Linux

* `sudo apt-get update -y`
* `sudo apt-get install -y thrift-compiler`


#### Setup instructions / Run platform

*  Visit [infra](infra/README.md) and run the docker-containers

* Make sure your maven points jdk11, to test, execute: `mvn -v`, you should see something like this output:
    * ```text
          mnchristidis@nchristidis-GL502VMK ~/Desktop/pollfish-chriniko $ mvn -v
          Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
          Maven home: /opt/apache/maven-3.6.3
          Java version: 11.0.8, vendor: Ubuntu, runtime: /usr/lib/jvm/java-11-openjdk-amd64
          Default locale: en_US, platform encoding: UTF-8
          OS name: "linux", version: "5.3.0-45-generic", arch: "amd64", family: "unix"

        ```

* Now, in the root folder, execute: `mvn clean install`


* Run thrift-server first:
    * ` cd thrift-server/`
    * `java -jar target/thrift-server-1.0-SNAPSHOT.jar`
    
    * Output:
        ```text
          nchristidis@nchristidis-GL502VMK ~/Desktop/pollfish-chriniko/thrift-server $ java -jar target/thrift-server-1.0-SNAPSHOT.jar 
          [INFO ] 2020-11-26 13:24:34.855 [main] LoggingEventServer - will use server flavour: THREADED_SELECTOR_SERVER
          SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
          SLF4J: Defaulting to no-operation (NOP) logger implementation
          SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
          [INFO ] 2020-11-26 13:24:35.039 [main] LoggingEventServer - Starting the server... 
          [INFO ] 2020-11-26 13:24:35.039 [server-status-checker] LoggingEventServer - server is not up and running... will ping again...
          [INFO ] 2020-11-26 13:24:36.289 [server-status-checker] LoggingEventServer - server is up and running
         
        ```
    
* Run clients-simulator:
    * ` cd clients-simulator/`
    * ` java -jar target/clients-simulator-1.0-SNAPSHOT.jar `
    
    * Output:
        ```text
          nchristidis@nchristidis-GL502VMK ~/Desktop/pollfish-chriniko/clients-simulator $ java -jar target/clients-simulator-1.0-SNAPSHOT.jar 
          SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
          SLF4J: Defaulting to no-operation (NOP) logger implementation
          SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
          [INFO ] 2020-11-26 13:36:03.615 [client-sim-coord-4] ClientSimulationsCoordinator - clientFlow success, message: 
             LoggingEvent(id:20ffb16e-b930-4167-89de-1a975cf8a454, origin:monitor_service_0, time:1606390563140, metadata:{}, m:<LoggingEventPayload monitorServiceEvent:MonitorServiceEvent(serviceId:d814c324-778f-4cd1-a30c-94b93f4a7ad4, serviceUrl:http://service.com, type:LOW_MEMORY)>, priority:NORMAL)
             LoggingEvent(id:479dde6d-dbc4-4e0b-bf83-9adfe61acdac, origin:wallet_service_0, time:1606390563347, metadata:{}, m:<LoggingEventPayload transactionEvent:TransactionEvent(txId:a34419ea-996f-4ea2-9ace-a59ff1b50c27, fromAccountId:1, toAccountId:2, amount:6400.0)>, priority:NORMAL)
             LoggingEvent(id:a958f6a5-2e23-400c-8da0-621f0a795f7d, origin:user_service_1, time:1606390563482, metadata:{somekey1=somevalue1, somekey2=somevalue2, somekey3=somevalue3}, m:<LoggingEventPayload userInfoEvent:UserInfoEvent(userId:9ded0bf0-d1bf-4cdd-9596-034bfd7c788c, type:<UserInfoEventType loggedOut:LoggedOut(durationOfLoginInSecs:355)>)>, priority:CRITICAL)
          
          [INFO ] 2020-11-26 13:36:03.615 [client-sim-coord-10] ClientSimulationsCoordinator - clientFlow success, message: 
             LoggingEvent(id:20ffb16e-b930-4167-89de-1a975cf8a454, origin:monitor_service_0, time:1606390563140, metadata:{}, m:<LoggingEventPayload monitorServiceEvent:MonitorServiceEvent(serviceId:d814c324-778f-4cd1-a30c-94b93f4a7ad4, serviceUrl:http://service.com, type:LOW_MEMORY)>, priority:NORMAL)
             LoggingEvent(id:479dde6d-dbc4-4e0b-bf83-9adfe61acdac, origin:wallet_service_0, time:1606390563347, metadata:{}, m:<LoggingEventPayload transactionEvent:TransactionEvent(txId:a34419ea-996f-4ea2-9ace-a59ff1b50c27, fromAccountId:1, toAccountId:2, amount:6400.0)>, priority:NORMAL)
             LoggingEvent(id:a958f6a5-2e23-400c-8da0-621f0a795f7d, origin:user_service_1, time:1606390563482, metadata:{somekey1=somevalue1, somekey2=somevalue2, somekey3=somevalue3}, m:<LoggingEventPayload userInfoEvent:UserInfoEvent(userId:9ded0bf0-d1bf-4cdd-9596-034bfd7c788c, type:<UserInfoEventType loggedOut:LoggedOut(durationOfLoginInSecs:355)>)>, priority:CRITICAL)
          
          [INFO ] 2020-11-26 13:36:03.615 [client-sim-coord-5] ClientSimulationsCoordinator - clientFlow success, message: 
             LoggingEvent(id:20ffb16e-b930-4167-89de-1a975cf8a454, origin:monitor_service_0, time:1606390563140, metadata:{}, m:<LoggingEventPayload monitorServiceEvent:MonitorServiceEvent(serviceId:d814c324-778f-4cd1-a30c-94b93f4a7ad4, serviceUrl:http://service.com, type:LOW_MEMORY)>, priority:NORMAL)
             LoggingEvent(id:479dde6d-dbc4-4e0b-bf83-9adfe61acdac, origin:wallet_service_0, time:1606390563347, metadata:{}, m:<LoggingEventPayload transactionEvent:TransactionEvent(txId:a34419ea-996f-4ea2-9ace-a59ff1b50c27, fromAccountId:1, toAccountId:2, amount:6400.0)>, priority:NORMAL)
             LoggingEvent(id:a958f6a5-2e23-400c-8da0-621f0a795f7d, origin:user_service_1, time:1606390563482, metadata:{somekey1=somevalue1, somekey2=somevalue2, somekey3=somevalue3}, m:<LoggingEventPayload userInfoEvent:UserInfoEvent(userId:9ded0bf0-d1bf-4cdd-9596-034bfd7c788c, type:<UserInfoEventType loggedOut:LoggedOut(durationOfLoginInSecs:355)>)>, priority:CRITICAL)

        ```

    
* Run kafka-consumer:
    * `cd kafka-consumer/`
    * `java -jar target/kafka-consumer-1.0-SNAPSHOT.jar`
    
    * Output:
        
        ```text
      
          nchristidis@nchristidis-GL502VMK ~/Desktop/pollfish-chriniko/kafka-consumer $ java -jar target/kafka-consumer-1.0-SNAPSHOT.jar 
          SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
          SLF4J: Defaulting to no-operation (NOP) logger implementation
          SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
          WARNING: sun.reflect.Reflection.getCallerClass is not supported. This will impact performance.
          [DEBUG] 2020-11-26 13:43:45.189 [main] CassandraStore - will execute query: CREATE KEYSPACE IF NOT EXISTS poll_fish WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
          [DEBUG] 2020-11-26 13:43:45.209 [main] CassandraStore - will execute query: USE poll_fish;
          [DEBUG] 2020-11-26 13:43:45.215 [main] CassandraStore - will execute query: drop table if exists logging_evts_by_type_ord_time_desc;
          [DEBUG] 2020-11-26 13:43:46.882 [main] CassandraStore - will execute query: create table logging_evts_by_type_ord_time_desc(    version  int,    id       uuid,    origin   text,    time     timestamp,    metadata map<text, text>,    type     text,    priority text,    payload  text,    primary key ( type, time )) WITH CLUSTERING ORDER BY (time desc);
          [DEBUG] 2020-11-26 13:43:48.027 [main] CassandraStore - will execute query: drop table if exists logging_evts_by_priority_ord_time_desc;
          [DEBUG] 2020-11-26 13:43:49.344 [main] CassandraStore - will execute query: create table logging_evts_by_priority_ord_time_desc(    version  int,    id       uuid,    origin   text,    time     timestamp,    metadata map<text, text>,    type     text,    priority text,    payload  text,    primary key ( priority, time )) WITH CLUSTERING ORDER BY (time desc);
          [DEBUG] 2020-11-26 13:43:50.565 [main] CassandraStore - will execute query: drop table if exists logging_evts_by_priority_by_type_ord_time_desc;
          [DEBUG] 2020-11-26 13:43:51.891 [main] CassandraStore - will execute query: create table logging_evts_by_priority_by_type_ord_time_desc(    version  int,    id       uuid,    origin   text,    time     timestamp,    metadata map<text, text>,    type     text,    priority text,    payload  text,    primary key ( (priority, type), time )) WITH CLUSTERING ORDER BY (time desc);
          [DEBUG] 2020-11-26 13:43:53.035 [main] CassandraStore - will execute query: drop table if exists logging_evts_by_or_by_prio_by_typ_ord_t_desc;
          [DEBUG] 2020-11-26 13:43:54.480 [main] CassandraStore - will execute query: create table logging_evts_by_or_by_prio_by_typ_ord_t_desc(    version  int,    id       uuid,    origin   text,    time     timestamp,    metadata map<text, text>,    type     text,    priority text,    payload  text,    primary key ( (origin, priority, type), time )) WITH CLUSTERING ORDER BY (time desc);
          [DEBUG] 2020-11-26 13:43:55.636 [main] CassandraStore - will execute query: drop table if exists logging_evts_by_or_ord_prio_typ_t_desc;
          [DEBUG] 2020-11-26 13:43:56.993 [main] CassandraStore - will execute query: create table logging_evts_by_or_ord_prio_typ_t_desc(    version  int,    id       uuid,    origin   text,    time     timestamp,    metadata map<text, text>,    type     text,    priority text,    payload  text,    primary key ( (origin), priority, type, time )) WITH CLUSTERING ORDER BY (priority asc, type asc, time desc);
          [INFO ] 2020-11-26 13:43:58.672 [main] BootstrapKafkaConsumer$ - controls created: 1
          [INFO] [11/26/2020 13:43:58.682] [logging-events-consumer-akka.actor.default-dispatcher-4] [SingleSourceLogic(akka://logging-events-consumer)] [4cc11] Starting. StageActor Actor[akka://logging-events-consumer/system/StreamSupervisor-0/$$a#608205030]
          [DEBUG] 2020-11-26 13:43:59.709 [logging-events-consumer-akka.kafka.io-blocking-dispatcher-32] LoggingEventDeserializer - >>>> event consumed: LoggingEvent(id:a650fd9e-468d-46f0-8917-edbd9eeea0d6, origin:wallet_service_2, time:1606332814207, metadata:{somekey1=somevalue1, somekey2=somevalue2, somekey3=somevalue3}, m:<LoggingEventPayload transactionEvent:TransactionEvent(txId:d2ab632e-f1a8-4df4-9fc3-79842adc129c, fromAccountId:1, toAccountId:2, amount:6400.0)>, priority:NORMAL)
          [DEBUG] 2020-11-26 13:43:59.709 [logging-events-consumer-akka.kafka.io-blocking-dispatcher-26] LoggingEventDeserializer - >>>> event consumed: LoggingEvent(id:c3356ad2-c1a1-4b72-b658-1477d29795f7, origin:wallet_service_2, time:1606330544501, metadata:{}, m:<LoggingEventPayload transactionEvent:TransactionEvent(txId:7f7dffdb-ae2b-4102-b25d-20b73af81b6e, fromAccountId:1, toAccountId:2, amount:6400.0)>, priority:NORMAL)
          [DEBUG] 2020-11-26 13:43:59.710 [logging-events-consumer-akka.kafka.io-blocking-dispatcher-38] LoggingEventDeserializer - >>>> event consumed: LoggingEvent(id:52a028e2-81cc-41ca-8da1-aa5cc547e4df, origin:wallet_service_2, time:1606351727106, metadata:{}, m:<LoggingEventPayload transactionEvent:TransactionEvent(txId:2690dfad-8b43-4c3b-82d5-17d7d10f2991, fromAccountId:1, toAccountId:2, amount:6400.0)>, priority:NORMAL)
          [DEBUG] 2020-11-26 13:43:59.710 [logging-events-consumer-akka.kafka.io-blocking-dispatcher-24] LoggingEventDeserializer - >>>> event consumed: LoggingEvent(id:7b17d2fe-f4e6-4b56-a8c3-66b6499c907b, origin:monitor_service_1, time:1606330542972, metadata:{somekey1=somevalue1, somekey2=somevalue2, somekey3=somevalue3}, m:<LoggingEventPayload monitorServiceEvent:MonitorServiceEvent(serviceId:dd6f497d-0df6-4d50-a748-21b514cc064b, serviceUrl:http://service.com, type:CPU_HOT)>, priority:CRITICAL)
          [DEBUG] 2020-11-26 13:43:59.710 [logging-events-consumer-akka.kafka.io-blocking-dispatcher-25] LoggingEventDeserializer - >>>> event consumed: LoggingEvent(id:e998f0b4-e44c-40d8-8d32-6586964a1fc8, origin:wallet_service_1, time:1606330543099, metadata:{}, m:<LoggingEventPayload transactionEvent:TransactionEvent(txId:eed32771-40e8-4de4-90ed-23b7516b7d1f, fromAccountId:1, toAccountId:2, amount:6400.0)>, priority:NORMAL)
          [DEBUG] 2020-11-26 13:43:59.710 [logging-events-consumer-akka.kafka.io-blocking-dispatcher-36] LoggingEventDeserializer - >>>> event consumed: LoggingEvent(id:3498fb2f-6d15-4150-9bb6-49ef43fcda14, origin:monitor_service_2, time:1606351711683, metadata:{}, m:<LoggingEventPayload monitorServiceEvent:MonitorServiceEvent(serviceId:dbe88a28-6344-4c97-b84c-62daf8f3d5a9, serviceUrl:http://service.com, type:CPU_HOT)>, priority:NORMAL)

        ```

#### Future Work / TODOs

* During to personal time limitation, no unit tests and integration tests have been written which is UNACCEPTABLE
for real production systems.
    * first thing to do, is to create an it-suite maven module where it will have logic to test all the parts of the platform
    * then we can proceed with integration tests per module and unit tests
    * inverted pyramid of testing is what most of times I prefer.

* Create 3-node cassandra cluster with docker-containers and produce heavy traffic to check how it behaves, experiment, etc.

* Benchmark, check behaviour with a tool such as VisualVM

* If we see that our thrift-server cannot hold the load, we can run multiple
thrift-servers (JVM processes) and made modifications to store the incoming log-event message to a distributed queue
provided from implementers such as Hazelcast (https://docs.hazelcast.org/docs/latest/manual/html-single/#queue), so to
store log-events there, and have multiple thrift-servers to consume from that.

* We could use a custom partitioner to send log-events with critical or medium priority to dedicated partitions, for example
to be processed faster and get seen by a human operator-support engineer.
