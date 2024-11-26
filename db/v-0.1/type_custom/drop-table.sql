alter table if exists type_custom
    drop constraint FK_TypeCustom_CustomTypesSyntax

GO
alter table if exists type_custom
    drop constraint FK_TypeCustom_NoteBlocks

GO

drop table type_custom
GO
