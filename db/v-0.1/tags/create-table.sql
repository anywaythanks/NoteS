create table tags
(
    id   bigint   not null,
    name char(64) not null unique,
    primary key (id)
)

GO

create sequence account_tag_seq
    minvalue 1
    start with 1
    increment by 50
    cache 5

GO

alter sequence account_tag_seq owned by tags.id

GO