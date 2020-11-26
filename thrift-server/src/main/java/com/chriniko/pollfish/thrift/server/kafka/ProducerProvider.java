package com.chriniko.pollfish.thrift.server.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.utils.Bytes;

import java.util.Properties;

public class ProducerProvider {


    public static Producer<String, Bytes> get(String bootstrap, String clientId) {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BytesSerializer.class.getName());

        setupBatchingAndCompression(props);
        setupRetriesInFlightTimeout(props);

        return new KafkaProducer<>(props);
    }

    private static void setupBatchingAndCompression(
            final Properties props) {
        // Linger up to 100 ms before sending batch if size not met
        props.put(ProducerConfig.LINGER_MS_CONFIG, 115);

        // Batch up to 64K buffer sizes.
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16_384 * 4);

        // Use Snappy compression for batch compression.
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
    }

    private static void setupRetriesInFlightTimeout(Properties props) {
        // Only one in-flight messages per Kafka broker connection
        // - max.in.flight.requests.per.connection (default 5)
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1); // Note: when is 1 we maintain the order in partition level.

        // Set the number of retries - retries
        props.put(ProducerConfig.RETRIES_CONFIG, 3);

        // Request timeout - request.timeout.ms
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15_000);

        // Only retry after specified time.
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_150);
    }

}
