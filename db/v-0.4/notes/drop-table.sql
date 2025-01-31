alter table if exists notes
    drop constraint FK_Notes_Notes_Prev

GO

alter table if exists notes
    drop constraint FK_Notes_Note_Type

GO

alter table if exists notes
    drop column next;

GO

alter table if exists notes
    drop column type;

GO