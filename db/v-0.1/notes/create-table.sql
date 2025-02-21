create table notes (
      id bigint not null,
      title char(128) not null unique,
      account_id bigint not null,
      modified_by timestamp(6) with time zone not null,
      created_on timestamp(6) with time zone not null,
      primary key (id)
)

GO

create sequence note_id_seq
    minvalue 1
    start with 1
    increment by 50
    cache 5

GO

alter sequence note_id_seq owned by notes.id

GO

alter table if exists notes 
    add constraint FK_Notes_Accounts
    foreign key (account_id)
    references accounts 

GO
