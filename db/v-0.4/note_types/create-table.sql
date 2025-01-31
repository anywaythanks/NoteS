create sequence note_types_id_seq
    minvalue 1
    start with 100
    increment by 50
    cache 5

GO

create table note_types (
      id int not null default nextval('note_types_id_seq'::regclass),
      name char(64) not null unique,
      created_on timestamp(6) with time zone not null default now(),
      primary key (id)
)

GO

alter sequence note_types_id_seq owned by note_types.id