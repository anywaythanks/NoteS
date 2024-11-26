create table custom_types_syntax
(
    id     bigint          not null,
    syntax char(32) unique not null,
    primary key (id)
)

GO

create sequence custom_types_syntax_seq
    minvalue 1
    start with 1
    increment by 50
    cache 5

GO

alter sequence custom_types_syntax_seq owned by custom_types_syntax.id

GO