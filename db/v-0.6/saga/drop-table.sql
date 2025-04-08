alter table if exists accounts
    alter column name type char(32);

GO

alter table if exists accounts
    alter column uuid type char(64);

GO
