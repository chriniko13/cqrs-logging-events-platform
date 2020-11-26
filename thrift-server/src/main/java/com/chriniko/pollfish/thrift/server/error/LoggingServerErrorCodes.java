package com.chriniko.pollfish.thrift.server.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LoggingServerErrorCodes {


    SERIALIZATION_ERROR(1000);


    @Getter
    private final int code;
}
