alter table if exists notes
    drop column title;

GO

alter table if exists notes
    drop column syntax_type_id;

GO

alter table if exists notes
    drop column description;

GO

alter table if exists notes
    drop column elastic_uuid;
GO

alter table if exists notes
    add column elastic_uuid UUID default gen_random_uuid() not null unique;

GO

alter table if exists notes
    add column state_id integer default 0 not null
        constraint fk_notes_notes_states references note_states;

GO
alter table if exists notes
    rename column prev to main_note;

GO

alter table if exists notes
    add column commit_to bigint unique
        constraint fk_notes_commits references commits;

GO