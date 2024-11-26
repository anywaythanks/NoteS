create table note_block_types (
      id bigint not null,
      note_block_id bigint not null unique,
      created_on timestamp(6) with time zone not null,
      primary key (id)
)

GO

create sequence note_block_types_seq
    minvalue 1
    start with 1
    increment by 50
    cache 5

GO

alter sequence note_block_types_seq owned by note_block_types.id

GO

alter table if exists note_block_types
    add constraint FK_NoteBlockTypes_NoteBlocks
    foreign key (note_block_id)
    references note_blocks 

GO
