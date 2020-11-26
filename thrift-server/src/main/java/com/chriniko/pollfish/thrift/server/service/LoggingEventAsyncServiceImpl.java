package com.chriniko.pollfish.thrift.server.service;

import com.chriniko.pollfish.thrift.server.protocol.LoggingEvent;
import com.chriniko.pollfish.thrift.server.protocol.LoggingEventService;
import com.chriniko.pollfish.thrift.server.protocol.LoggingEventServiceHealthStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

import java.util.Set;

@Log4j2
public class LoggingEventAsyncServiceImpl implements LoggingEventService.AsyncIface {

    private final LoggingEventServiceImpl delegate;

    public LoggingEventAsyncServiceImpl(Producer<String, Bytes> producer, ObjectMapper mapper, String topicName, AdminClient adminClient) {
        delegate = new LoggingEventServiceImpl(producer, topicName, adminClient, mapper);
    }

    @Override
    public void save(LoggingEvent evt, AsyncMethodCallback<LoggingEvent> resultHandler) throws TException {
        try {
            log.debug("async service used for save"); // Note: added, just to check that it is dispatching work to the specified thread pool.
            LoggingEvent result = delegate.save(evt);
            resultHandler.onComplete(result);
        } catch (Exception e) {
            log.error("error occurred during save of logging event, msg: " + e.getMessage(), e);
            resultHandler.onError(e);
        }
    }

    @Override
    public void batchSave(Set<LoggingEvent> evts, AsyncMethodCallback<Set<LoggingEvent>> resultHandler) throws TException {
        try {
            log.debug("async service used for batch-save"); // Note: added, just to check that it is dispatching work to the specified thread pool.
            Set<LoggingEvent> result = delegate.batchSave(evts);
            resultHandler.onComplete(result);
        } catch (Exception e) {
            log.error("error occurred during save of logging event[batch], msg: " + e.getMessage(), e);
            resultHandler.onError(e);
        }
    }

    @Override
    public void healthStatus(AsyncMethodCallback<LoggingEventServiceHealthStatus> resultHandler) throws TException {
        try {
            log.debug("async service used for health status"); // Note: added, just to check that it is dispatching work to the specified thread pool.
            LoggingEventServiceHealthStatus result = delegate.healthStatus();
            resultHandler.onComplete(result);
        } catch (Exception e) {
            log.error("error occurred during retrieval of health status, msg: " + e.getMessage(), e);
            resultHandler.onError(e);
        }
    }


}
