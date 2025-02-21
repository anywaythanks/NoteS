alter table if exists note_blocks
    add content text not null;

GO

alter table if exists note_blocks
    add syntax_types_id int not null
GO

alter table if exists note_blocks
    add constraint FK_NoteBlocks_CustomTypesSyntax
    foreign key (syntax_types_id)
    references custom_types_syntax

GO

