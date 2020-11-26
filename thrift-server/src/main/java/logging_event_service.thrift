namespace java com.chriniko.pollfish.thrift.server.protocol

// ------------------------ message payloads ---------------------------------------------------------------------------


enum InfoEventType {
    LOW_MEMORY,
    CPU_IDLE,
    CPU_HOT
}

struct MonitorServiceEvent {
    1: optional i16 v = 1, // indicates version number

    2: required string serviceId,
    3: required string serviceUrl,
    4: required InfoEventType type
}

// ----

struct TransactionEvent {
    1: optional i16 v = 1, // indicates version number

    2: required string txId,
    3: required string fromAccountId,
    4: required string toAccountId,
    5: required double amount
}

// ----

struct UserInfoEvent {

    1: optional i16 v = 1, // indicates version number

    2: required string userId,

    3: required UserInfoEventType type
}

union UserInfoEventType {
    1: LoggedIn loggedIn,
    2: LoggedOut loggedOut,
    3: ClickedOnAdvertisement clickedOnAdvertisement
}


struct LoggedIn {
    1: required string url
}

struct LoggedOut {
    1: required i64 durationOfLoginInSecs
}

struct ClickedOnAdvertisement {
    1: required string clickUrl,
    2: required string callbackTracker
}


// ------------------------ logging event infra ------------------------------------------------------------------------

enum LoggingEventPriority {
    NORMAL,
    MEDIUM,
    CRITICAL
}

union LoggingEventPayload {
    1: MonitorServiceEvent monitorServiceEvent,
    2: TransactionEvent transactionEvent,
    3: UserInfoEvent userInfoEvent

    // Note: add your new events here.
}


 struct LoggingEvent {
    1: optional i16 v = 1, // indicates version number

    2: required string id, // this will be a UUID
    3: required string origin, // which system was the sender of this message

    4: required i64 time, // it will hold the epoch millis based on the "1970-01-01T00:00:00Z"

    5: optional map<string, string> metadata, // headers/metadata

    6: required LoggingEventPayload m, // message

    7: optional LoggingEventPriority priority = LoggingEventPriority.NORMAL,
 }

 exception NotValidInputException {
     1: i32 code,
     2: string description,
 }

 struct LoggingEventServiceHealthStatus {
    1: bool healthy,
    2: binary msg
 }

service LoggingEventService {

    LoggingEvent save(1:LoggingEvent evt) throws (1:NotValidInputException e)

    set<LoggingEvent> batchSave(1: set<LoggingEvent> evts) throws (1:NotValidInputException e)

    LoggingEventServiceHealthStatus healthStatus()
}