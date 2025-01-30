alter table if exists custom_types_syntax
    alter column id SET default nextval('account_id_seq'::regclass);
GO

alter table if exists custom_types_syntax
    add column created_on timestamp(6) default now();

GO