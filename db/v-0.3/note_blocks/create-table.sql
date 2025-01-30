alter table if exists note_blocks
    alter column id SET default nextval('note_blocks_seq'::regclass);

GO

alter table if exists note_blocks
    alter column created_on SET default now();

GO

alter table if exists note_blocks
    alter column modified_by SET default now();

GO

CREATE FUNCTION updater_modify_by_note_blocks()
    RETURNS trigger AS
$$
BEGIN
    NEW.modified_by := now();
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

GO

CREATE TRIGGER trigger_update_modify_by_note_blocks
    BEFORE UPDATE
    ON note_blocks
    FOR EACH ROW
EXECUTE PROCEDURE updater_modify_by_note_blocks();
GO