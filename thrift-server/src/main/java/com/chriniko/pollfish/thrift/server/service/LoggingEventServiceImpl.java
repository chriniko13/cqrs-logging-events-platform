package com.chriniko.pollfish.thrift.server.service;

import com.chriniko.pollfish.thrift.server.error.LoggingServerErrorCodes;
import com.chriniko.pollfish.thrift.server.protocol.LoggingEvent;
import com.chriniko.pollfish.thrift.server.protocol.LoggingEventService;
import com.chriniko.pollfish.thrift.server.protocol.LoggingEventServiceHealthStatus;
import com.chriniko.pollfish.thrift.server.protocol.NotValidInputException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.utils.Bytes;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Log4j2
public class LoggingEventServiceImpl implements LoggingEventService.Iface {

    private static final int TIME_MS = 1200;


    private final Producer<String, Bytes> kafkaProducer;
    private final String topic;
    private final AdminClient adminClient;
    private final ObjectMapper mapper;

    private final ThreadLocal<TSerializer> tSerializerThreadLocal = ThreadLocal.withInitial(TSerializer::new);

    public LoggingEventServiceImpl(Producer<String, Bytes> kafkaProducer,
                                   String topic, AdminClient adminClient,
                                   ObjectMapper mapper) {
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
        this.adminClient = adminClient;
        this.mapper = mapper;
    }

    @Override
    public LoggingEvent save(LoggingEvent evt) throws TException {

        final byte[] bytes;
        try {
            bytes = tSerializerThreadLocal.get().serialize(evt);
        } catch (Exception e) {
            log.error("could not serialize logging event id: " + evt.id + " and origin: " + evt.origin, e);
            throw new NotValidInputException(LoggingServerErrorCodes.SERIALIZATION_ERROR.getCode(), e.getMessage());
        }


        ProducerRecord<String, Bytes> producerRecord = new ProducerRecord<>(topic, null, Instant.now().toEpochMilli(),
                evt.id, Bytes.wrap(bytes)
        );

        try {
            RecordMetadata recordMetadata = kafkaProducer.send(producerRecord).get(TIME_MS, TimeUnit.MILLISECONDS);
            log.info("logging event sent to infra, recordMetadata: " + recordMetadata);
            return evt;

        } catch (InterruptedException e) {
            log.error("error occurred during save of logging event, msg: " + e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw new TException("save failure" + e.getMessage(), e);
        } catch (ExecutionException | TimeoutException e) {
            log.error("error occurred during save of logging event, msg: " + e.getMessage(), e);
            throw new TException("save failure" + e.getMessage(), e);
        }
    }

    @Override
    public Set<LoggingEvent> batchSave(Set<LoggingEvent> evts) throws TException {
        for (LoggingEvent evt : evts) {
            save(evt);
        }
        return evts;
    }

    @Override
    public LoggingEventServiceHealthStatus healthStatus() throws TException {
        try {
            final LoggingEventServiceHealthStatus result = new LoggingEventServiceHealthStatus();
            final StringBuilder sb = new StringBuilder();

            sb.append("==== topic listings ====\n");
            for (TopicListing topicListing : adminClient.listTopics().listings().get(TIME_MS * 3L, TimeUnit.MILLISECONDS)) {
                sb.append(topicListing).append("\n");
            }

            sb.append("\n\n");

            result.setHealthy(sb.length() != 0);
            result.setMsg(mapper.writeValueAsBytes(sb.toString()));

            return result;

        } catch (Exception e) {
            log.error("error occurred during retrieval of health status, msg: " + e.getMessage(), e);
            throw new TException("healthStatus failure: " + e.getMessage(), e);
        }
    }

}
