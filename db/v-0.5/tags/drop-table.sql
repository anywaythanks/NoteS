ALTER TABLE tags DROP CONSTRAINT IF EXISTS tags_pkey;

GO

ALTER TABLE tags DROP CONSTRAINT IF EXISTS tags_id_key;

GO

ALTER TABLE tags DROP CONSTRAINT IF EXISTS FK_Tags_Accounts;

GO

alter table if exists tags
    drop account_id

GO

ALTER TABLE tags ADD CONSTRAINT tags_name_key UNIQUE ("name");

GO

ALTER TABLE tags ADD CONSTRAINT tags_pkey PRIMARY KEY (id);

GO
