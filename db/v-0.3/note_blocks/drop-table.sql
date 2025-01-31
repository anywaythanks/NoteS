alter table if exists note_blocks
    alter column id drop default;

GO

alter table if exists note_blocks
    alter column created_on drop default;

GO

alter table if exists note_blocks
    alter column modified_by drop default;

GO

drop trigger trigger_update_modify_by_note_blocks on note_blocks;

GO

drop function updater_modify_by_note_blocks;

GO