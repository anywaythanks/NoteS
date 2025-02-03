alter table if exists notes
    add type int not null default 0;

GO

alter table if exists notes
    add prev int null;
GO

alter table if exists notes
    add path char(128) not null unique;

GO

alter table if exists notes
    add is_public boolean not null default false;

GO

alter table if exists notes
    add constraint FK_Notes_Notes_Prev
        foreign key (prev)
            references notes

GO

alter table if exists notes
    add constraint FK_Notes_Note_Type
        foreign key (type)
            references note_types

GO