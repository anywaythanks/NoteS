alter table if exists notes
    alter column title type varchar(128);

GO

alter table if exists notes
    alter column path type varchar(128);

GO

alter table if exists notes
    alter column description type varchar(2048);

GO

alter table if exists notes
    alter column elastic_uuid type varchar(128);
GO

alter table if exists notes
    alter column prev type bigint;

GO
