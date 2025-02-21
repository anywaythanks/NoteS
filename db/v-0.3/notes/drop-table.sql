alter table if exists notes
    alter column id drop default;

GO

alter table if exists notes
    alter column created_on drop default;

GO

alter table if exists notes
    alter column modified_by drop default;

GO

drop trigger trigger_update_modify_by_notes on notes;

GO

drop function updater_modify_by_notes;

GO