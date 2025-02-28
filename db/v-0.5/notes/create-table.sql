alter table if exists notes
    add syntax_type_id int not null default 0;

GO

alter table if exists notes
    add constraint FK_Notes_Syntax_Type
        foreign key (syntax_type_id)
            references custom_types_syntax

GO

alter table if exists notes
    add elastic_uuid char(128) not null unique;

GO

alter table if exists notes
    drop constraint notes_title_key;

GO

alter table if exists notes
    add description char(2048) not null default '';

GO