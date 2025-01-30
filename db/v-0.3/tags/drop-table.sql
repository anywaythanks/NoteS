alter table if exists tags
    alter column id SET default null;
GO

alter table if exists tags
    drop column created_on;
GO