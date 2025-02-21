alter table if exists custom_types_syntax
    alter column id drop default;
GO

alter table if exists custom_types_syntax
    drop column created_on;
GO