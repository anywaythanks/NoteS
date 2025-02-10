create table accounts (
      id bigint not null,
      uuid char(64) not null unique,
      name char(32) not null unique,
      modified_by timestamp(6) with time zone not null,
      created_on timestamp(6) with time zone not null,
      primary key (id)
)

GO

create sequence account_id_seq
    minvalue 1
    start with 1
    increment by 50
    cache 5

GO

alter sequence account_id_seq owned by accounts.id

GO