




### Thrift Server

#


#### Description

* Thrift server holds the protocol (thrift files), so if anyone wants to talk to this server, should have it as a dependency in order to have the generated
stubs/files (eg: `thrift-client` which is the de facto library to talk to `thrift-server`)


#### Generate java stubs from defined thrift file:
* Execute in the directory: `~/Desktop/<your_dir>/thrift-server/src/main/java` the following command: `thrift -r -out . --gen java logging_event_service.thrift`
* So now you should have the generated files



