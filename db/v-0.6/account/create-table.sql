alter table if exists accounts
    alter column name type varchar(128);

GO

alter table if exists accounts
    alter column uuid type varchar(128);

GO
