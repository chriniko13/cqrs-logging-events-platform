package com.chriniko.pollfish.thrift.server.infra;

import com.chriniko.pollfish.thrift.server.kafka.AdminClientProvider;
import com.chriniko.pollfish.thrift.server.kafka.ProducerProvider;
import com.chriniko.pollfish.thrift.server.protocol.LoggingEventService;
import com.chriniko.pollfish.thrift.server.service.LoggingEventAsyncServiceImpl;
import com.chriniko.pollfish.thrift.server.service.LoggingEventServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

@Log4j2
public class LoggingEventServer {

    private TServer server;

    private final String kafkaBrokers;
    private final String clientId;
    private final String topicName;
    private final int port;
    private final int poolSize;

    public LoggingEventServer() {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setFileName("application.properties"));
        try {
            Configuration config = builder.getConfiguration();

            kafkaBrokers = config.getString("kafka-brokers");
            clientId = config.getString("client-id");
            topicName = config.getString("topic-name");
            port = config.getInt("server-port");
            poolSize = config.getInt("io-intensive-pool.size");

        } catch (ConfigurationException cex) {
            throw new IllegalStateException(cex);
        }

    }

    public void start(ServerFlavour flavour) throws TTransportException {

        log.info("will use server flavour: {}", flavour);
        switch (flavour) {
            case SIMPLE:
                server = simpleServer();
                break;
            case THREADED_SELECTOR_SERVER:
                server = threadedSelectorServer();
                break;
            case THREAD_POOL:
                server = threadPoolServer();
                break;
        }

        Thread t = getCheckServerHealthWorker();
        t.setName("server-status-checker");
        t.start();

        log.info("Starting the server... ");
        server.serve();
    }

    public void stop() {
        if (server != null && server.isServing()) {
            log.info("Stopping the server... ");

            server.stop();

            log.info("server stopped.");
        }
    }


    private LoggingEventService.Processor<LoggingEventService.Iface> getProcessor() {
        final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        final Producer<String, Bytes> producer = ProducerProvider.get(kafkaBrokers, clientId + "_" + jvmName);
        final ObjectMapper mapper = new ObjectMapper();
        final AdminClient adminClient = AdminClientProvider.get(kafkaBrokers);


        final LoggingEventService.Iface service = new LoggingEventServiceImpl(producer, topicName, adminClient, mapper);
        return new LoggingEventService.Processor<>(service);
    }


    private LoggingEventService.AsyncProcessor<LoggingEventService.AsyncIface> getAsyncProcessor() {
        final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        final Producer<String, Bytes> producer = ProducerProvider.get(kafkaBrokers, clientId + "_" + jvmName);
        final ObjectMapper mapper = new ObjectMapper();
        final AdminClient adminClient = AdminClientProvider.get(kafkaBrokers);

        final LoggingEventService.AsyncIface service = new LoggingEventAsyncServiceImpl(producer, mapper, topicName, adminClient);
        return new LoggingEventService.AsyncProcessor<>(service);
    }


    /**
     * Important Note: not good option for production-grade environment.
     *
     * @return
     * @throws TTransportException
     */
    private TSimpleServer simpleServer() throws TTransportException {
        return new TSimpleServer(
                new TServer
                        .Args(new TServerSocket(port))
                        .processor(getProcessor())
        );
    }


    /**
     * Important Note: if you choose this server flavour, then you will need to use the `LoggingEventServiceSyncClient.scala`
     * from thrift-client module in order to interact with.
     *
     * @return
     * @throws TTransportException
     */
    private TThreadPoolServer threadPoolServer() throws TTransportException {
        return new TThreadPoolServer(
                new TThreadPoolServer.Args(new TServerSocket(port))
                        .executorService(ioIntensive())
                        .processor(getProcessor())
        );
    }


    //

    /**
     * Important Note: if you choose this server flavour, then you will need to use the `LoggingEventServiceAsyncClient.scala`
     * from thrift-client module in order to interact with.
     *
     * @return
     * @throws TTransportException
     */
    private TThreadedSelectorServer threadedSelectorServer() throws TTransportException {
        return new TThreadedSelectorServer(
                new TThreadedSelectorServer.Args(new TNonblockingServerSocket(port))
                        .executorService(ioIntensive())
                        .selectorThreads(Runtime.getRuntime().availableProcessors() + 1)
                        .processor(getAsyncProcessor())
        );
    }


    private ExecutorService ioIntensive() {
        return Executors.newFixedThreadPool(poolSize, new ThreadFactory() {
            private AtomicInteger idx = new AtomicInteger();

            @Override
            public Thread newThread(Runnable runnable) {
                Thread t = new Thread(runnable);
                t.setName("log-event-server-worker-" + idx.getAndIncrement());
                return t;
            }
        });
    }

    private Thread getCheckServerHealthWorker() {
        return new Thread(() -> {
            boolean serverOk = false;

            while (!serverOk) {
                boolean serving = server.isServing();
                if (serving) {
                    serverOk = true;
                    log.info("server is up and running");
                } else {
                    log.info("server is not up and running... will ping again...");
                    LockSupport.parkNanos(TimeUnit.NANOSECONDS.convert(1250, TimeUnit.MILLISECONDS));
                }
            }
        });
    }


}
