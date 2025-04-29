alter table if exists notes
    alter column title type char(128);

GO

alter table if exists notes
    alter column path type char(128);

GO

alter table if exists notes
    alter column description type char(2048);

GO

alter table if exists notes
    alter column elastic_uuid type char(128);

GO

alter table if exists notes
    alter column prev type int;

GO
