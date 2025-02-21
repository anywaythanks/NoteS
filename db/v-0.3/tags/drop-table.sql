alter table if exists tags
    alter column id drop default;
GO

alter table if exists tags
    drop column created_on;
GO