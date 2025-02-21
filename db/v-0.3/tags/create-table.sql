alter table if exists tags
    alter column id SET default nextval('account_tag_seq'::regclass);
GO

alter table if exists tags
    add column created_on timestamp(6) with time zone default now();

GO