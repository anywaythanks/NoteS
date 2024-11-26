create table type_links
(
    block_id      bigint not null,
    link_block_id bigint not null check ( link_block_id != type_links.block_id ),
    primary key (block_id)
)

GO

alter table if exists type_links
    add constraint FK_TypeLinksId_NoteBlocks
        foreign key (block_id)
            references note_blocks
GO

alter table if exists type_links
    add constraint FK_TypeLinks_NoteBlocks
        foreign key (link_block_id)
            references note_blocks
GO
