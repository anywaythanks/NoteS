alter table if exists tags_notes_map
    drop constraint FK_TagsNoteMap_Note

GO
alter table if exists tags_notes_map
    drop constraint FK_TagsNoteMap_Tag

GO
drop table accounts

GO
