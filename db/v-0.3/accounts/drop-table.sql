alter table if exists accounts
    alter column id SET default null;

GO

alter table if exists accounts
    alter column created_on SET default null;

GO

alter table if exists accounts
    alter column modified_by SET default null;

GO

drop trigger trigger_update_modify_by_accounts on accounts;

GO

drop function updater_modify_by_accounts;

GO