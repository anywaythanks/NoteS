create table note_blocks
(
    id          bigint                      not null,
    note_id     bigint                      not null,
    "order"     int                         not null check ("order" >= 0),--УНИКАЛЬНО В ГРУППЕ NOTE_ID
    modified_by timestamp(6) with time zone not null,
    created_on  timestamp(6) with time zone not null,
    primary key (id)
)
GO

create sequence note_blocks_seq
    minvalue 1
    start with 1
    increment by 50
    cache 5
GO

alter sequence note_blocks_seq owned by note_blocks.id
GO

alter table if exists note_blocks
    add constraint FK_NoteBlocks_Note
        foreign key (note_id)
            references notes
GO
