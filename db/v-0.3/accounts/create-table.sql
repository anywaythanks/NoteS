alter table if exists accounts
    alter column id SET default nextval('account_id_seq'::regclass);

GO

alter table if exists accounts
    alter column created_on SET default now();

GO

alter table if exists accounts
    alter column modified_by SET default now();

GO

CREATE FUNCTION updater_modify_by_accounts()
    RETURNS trigger AS
$$
BEGIN
    NEW.modified_by := now();
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

GO

CREATE TRIGGER trigger_update_modify_by_accounts
    BEFORE UPDATE
    ON accounts
    FOR EACH ROW
EXECUTE PROCEDURE updater_modify_by_accounts();
GO