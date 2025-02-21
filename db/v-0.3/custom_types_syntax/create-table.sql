alter table if exists custom_types_syntax
    alter column id SET default nextval('custom_types_syntax_seq'::regclass);
GO

alter table if exists custom_types_syntax
    add column created_on timestamp(6) with time zone default now();

GO