alter table if exists tags
    add account_id bigint not null

GO

alter table if exists tags
    add constraint FK_Tags_Accounts
        foreign key (account_id)
            references accounts

GO

ALTER TABLE tags_notes_map drop  constraint if exists FK_TagsNoteMap_Tag;

GO

ALTER TABLE tags DROP CONSTRAINT IF EXISTS tags_pkey;

GO

ALTER TABLE tags DROP CONSTRAINT IF EXISTS tags_name_key;

GO

ALTER TABLE tags ADD CONSTRAINT tags_id_key UNIQUE (id);

GO

ALTER TABLE tags ADD CONSTRAINT tags_pkey PRIMARY KEY ("account_id", "name");

GO
 
alter table if exists tags_notes_map
    add constraint FK_TagsNoteMap_Tag
        foreign key (id_tag)
            references tags (id)

GO

alter table if exists tags
    add color int not null check ( color >= 0 and color <= 16777215 ) default 0;

GO