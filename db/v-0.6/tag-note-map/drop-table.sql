alter table if exists tags_notes_map
    alter column id_note type int;

GO

alter table if exists tags_notes_map
    alter column id_tag type int;

GO