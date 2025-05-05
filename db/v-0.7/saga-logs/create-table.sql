create sequence saga_logs_seq
    minvalue 1
    start with 1
    increment by 50
    cache 5;
GO


create table saga_logs
(
    id         bigint                      default nextval('saga_logs_seq') not null primary key,
    saga_uuid  uuid                                                        not null,
    note_id    bigint                                                      not null
        constraint fk_saga_logs_notes references notes,
    commit_id  bigint                                                      not null
        constraint fk_saga_logs_commits references commits,
    event_id   integer                                                     not null
        constraint fk_saga_logs_saga_events references saga_events,
    payload    json                                                        not null,
    created_on timestamp(6) with time zone default now()                   not null
);

GO

alter sequence saga_logs_seq owned by saga_logs.id;

GO