create sequence note_states_id_seq
    minvalue 1
    start with 1
    increment by 50
    cache 5;
GO


create table note_states
(
    id         integer                      default nextval('note_states_id_seq') not null primary key,
    name       varchar(128)                                                 not null,
    created_on timestamp(6) with time zone default now()                    not null
);

GO

alter sequence note_states_id_seq owned by note_states.id;

GO