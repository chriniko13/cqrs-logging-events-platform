package com.chriniko.pollfish.thrift.server.kafka;

import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;

public final class AdminClientProvider {

    private AdminClientProvider() {
    }

    public static AdminClient get(String bootstrap) {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        return AdminClient.create(config);
    }
}
