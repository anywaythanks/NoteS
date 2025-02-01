alter table if exists notes
    drop constraint FK_Notes_Syntax_Type

GO

alter table if exists notes
    drop column syntax_type_id;

GO