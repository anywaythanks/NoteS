alter table if exists type_links
    drop constraint FK_TypeLinksId_NoteBlocks

GO

alter table if exists type_links
    drop constraint FK_TypeLinks_NoteBlocks

GO

drop table type_links

GO

