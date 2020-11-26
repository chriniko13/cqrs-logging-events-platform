package com.chriniko.pollfish.thrift.server;

import com.chriniko.pollfish.thrift.server.infra.LoggingEventServer;
import com.chriniko.pollfish.thrift.server.infra.ServerFlavour;
import lombok.extern.log4j.Log4j2;
import org.apache.thrift.transport.TTransportException;


@Log4j2
public class BootstrapServer {

    public static void main(String[] args) throws TTransportException {
        final LoggingEventServer server = new LoggingEventServer();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("shutting down...");
            server.stop();
        }));

        server.start(ServerFlavour.THREADED_SELECTOR_SERVER);
    }
}
