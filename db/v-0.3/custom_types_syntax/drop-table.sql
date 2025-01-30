alter table if exists custom_types_syntax
    alter column id SET default null;
GO

alter table if exists custom_types_syntax
    drop column created_on;
GO