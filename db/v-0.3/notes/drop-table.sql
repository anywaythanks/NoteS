alter table if exists notes
    alter column id SET default null;

GO

alter table if exists notes
    alter column created_on SET default null;

GO

alter table if exists notes
    alter column modified_by SET default null;

GO

drop trigger trigger_update_modify_by_notes on notes;

GO

drop function updater_modify_by_notes;

GO