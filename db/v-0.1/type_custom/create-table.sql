create table type_custom
(
    block_id             bigint not null,
    content        text   not null,
    syntax_types_id int    not null,
    primary key (block_id)
)

GO

alter table if exists type_custom
    add constraint FK_TypeCustom_NoteBlocks
        foreign key (block_id)
            references note_blocks

GO

alter table if exists type_custom
    add constraint FK_TypeCustom_CustomTypesSyntax
        foreign key (syntax_types_id)
            references custom_types_syntax

GO

