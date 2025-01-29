create table type_subblocks (
      block_id bigint not null,
      primary key (block_id)
)

GO

alter table if exists type_subblocks
    add constraint FK_TypeSubblocks_NoteBlocks
    foreign key (block_id)
    references note_blocks

GO
