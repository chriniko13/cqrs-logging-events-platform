--##--
CREATE KEYSPACE IF NOT EXISTS poll_fish WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

--##--
USE poll_fish;


--##--
drop table if exists logging_evts_by_type_ord_time_desc;

--##--
create table logging_evts_by_type_ord_time_desc
(

    version  int,

    id       uuid,
    origin   text,

    time     timestamp,

    metadata map<text, text>,

    type     text,
    priority text,

    payload  text,

    primary key ( type, time )
) WITH CLUSTERING ORDER BY (time desc);


--##--
drop table if exists logging_evts_by_priority_ord_time_desc;

--##--
create table logging_evts_by_priority_ord_time_desc
(

    version  int,

    id       uuid,
    origin   text,

    time     timestamp,

    metadata map<text, text>,

    type     text,
    priority text,

    payload  text,

    primary key ( priority, time )
) WITH CLUSTERING ORDER BY (time desc);


--##--
drop table if exists logging_evts_by_priority_by_type_ord_time_desc;

--##--
create table logging_evts_by_priority_by_type_ord_time_desc
(

    version  int,

    id       uuid,
    origin   text,

    time     timestamp,

    metadata map<text, text>,

    type     text,
    priority text,

    payload  text,

    primary key ( (priority, type), time )
) WITH CLUSTERING ORDER BY (time desc);


--##--
drop table if exists logging_evts_by_or_by_prio_by_typ_ord_t_desc;

--##--
create table logging_evts_by_or_by_prio_by_typ_ord_t_desc
(

    version  int,

    id       uuid,
    origin   text,

    time     timestamp,

    metadata map<text, text>,

    type     text,
    priority text,

    payload  text,

    primary key ( (origin, priority, type), time )
) WITH CLUSTERING ORDER BY (time desc);



--##--
drop table if exists logging_evts_by_or_ord_prio_typ_t_desc;

--##--
create table logging_evts_by_or_ord_prio_typ_t_desc
(

    version  int,

    id       uuid,
    origin   text,

    time     timestamp,

    metadata map<text, text>,

    type     text,
    priority text,

    payload  text,

    primary key ( (origin), priority, type, time )
) WITH CLUSTERING ORDER BY (priority asc, type asc, time desc);