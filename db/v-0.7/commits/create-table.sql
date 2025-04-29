create sequence commits_id_seq
    minvalue 1
    start with 1
    increment by 50
    cache 5;

GO


create table commits
(
    id             bigint                      default nextval('commits_id_seq') not null primary key,
    note_id     bigint                                                        not null
        constraint fk_commits_notes references notes,
    title          varchar(128)                                                  not null,
    created_on     timestamp(6) with time zone default now()                     not null,
    syntax_type_id integer                                                       not null
        constraint fk_commits_syntax_type references custom_types_syntax,
    description    varchar(2048)                                                 not null,
    content        text                                                          not null
);

GO

alter sequence commits_id_seq owned by commits.id;

GO
