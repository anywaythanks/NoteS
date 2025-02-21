alter table if exists notes
    alter column id SET default nextval('note_id_seq'::regclass);

GO

alter table if exists notes
    alter column created_on SET default now();

GO

alter table if exists notes
    alter column modified_by SET default now();

GO

CREATE FUNCTION updater_modify_by_notes()
    RETURNS trigger AS
$$
BEGIN
    NEW.modified_by := now();
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

GO

CREATE TRIGGER trigger_update_modify_by_notes
    BEFORE UPDATE
    ON notes
    FOR EACH ROW
EXECUTE PROCEDURE updater_modify_by_notes();
GO