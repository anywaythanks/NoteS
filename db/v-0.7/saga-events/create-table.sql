create sequence events_id_seq
    minvalue 1
    start with 1
    increment by 50
    cache 5;
GO


create table saga_events
(
    id         integer                      default nextval('events_id_seq') not null primary key,
    name       varchar(128)                                                 not null,
    created_on timestamp(6) with time zone default now()                    not null
);

GO

alter sequence events_id_seq owned by saga_events.id;

GO