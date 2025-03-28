alter table if exists tags_notes_map
    alter column id_note type bigint;

GO

alter table if exists tags_notes_map
    alter column id_tag type bigint;

GO