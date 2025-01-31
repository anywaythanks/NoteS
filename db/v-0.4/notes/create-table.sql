alter table if exists notes
    add type int not null;

GO

alter table if exists notes
    add next int not null;

GO

alter table if exists notes
    add constraint FK_Notes_Notes_Prev
        foreign key (next)
            references notes

GO

alter table if exists notes
    add constraint FK_Notes_Note_Type
        foreign key (type)
            references note_types

GO
