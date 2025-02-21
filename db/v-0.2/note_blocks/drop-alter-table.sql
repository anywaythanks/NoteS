alter table if exists note_blocks
drop constraint FK_NoteBlocks_CustomTypesSyntax

GO

alter table if exists note_blocks
drop column syntax_types_id

GO

alter table if exists note_blocks
drop column content

GO