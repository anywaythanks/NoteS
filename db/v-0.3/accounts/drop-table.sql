alter table if exists accounts
    alter column id drop default;

GO

alter table if exists accounts
    alter column created_on drop default;

GO

alter table if exists accounts
    alter column modified_by drop default;

GO

drop trigger trigger_update_modify_by_accounts on accounts;

GO

drop function updater_modify_by_accounts;

GO