CREATE FUNCTION updater()
    RETURNS TRIGGER AS
$$
BEGIN
    IF new.account_id != old.account_id THEN
        RAISE EXCEPTION 'accounts.id not updatable';
    END IF;
    IF new.note_id != old.note_id THEN
        RAISE EXCEPTION 'notes.id not updatable';
    END IF;
    IF new.note_block_id != old.note_block_id THEN
        RAISE EXCEPTION 'note_blocks.id not updatable';
    END IF;
    IF new.account_name IS NOT NULL THEN
        UPDATE accounts SET name=new.account_name, modified_by = now() WHERE accounts.id = old.account_id;
    END IF;
    IF new.note_title IS NOT NULL THEN
        UPDATE notes SET title=new.note_title, modified_by = now() WHERE notes.id = old.note_id;
    END IF;

    IF new.block_content IS NOT NULL THEN
        CALL replace_content(old.note_block_id, new.block_content, new.syntax_id);
    END IF;
    IF new.block_links IS NOT NULL THEN
        CALL link_block(old.note_block_id, new.block_links);
    END IF;
    IF new.block_subblocks IS NOT NULL THEN
        CALL mark_subblock(new.block_subblocks);
    END IF;
    RETURN NEW;
END

$$ LANGUAGE plpgsql;

GO

CREATE TRIGGER updater_knows
    INSTEAD OF UPDATE
    ON knows
    FOR EACH ROW
EXECUTE PROCEDURE updater();

GO