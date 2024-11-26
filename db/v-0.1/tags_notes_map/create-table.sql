create table tags_notes_map
(
    id_tag  int not null,
    id_note bigint not null,
    primary key (id_tag, id_note)
)

GO

alter table if exists tags_notes_map
    add constraint FK_TagsNoteMap_Note
        foreign key (id_note)
            references notes

GO

alter table if exists tags_notes_map
    add constraint FK_TagsNoteMap_Tag
        foreign key (id_tag)
            references tags

GO