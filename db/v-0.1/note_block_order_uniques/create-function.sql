CREATE FUNCTION note_block_order_uniques()
    RETURNS TRIGGER AS
$$
DECLARE
    count_all  integer;
BEGIN
    count_all := (select count("order")
                  from note_blocks
                  where note_id = new.note_id and "order" = new."order"
                  group by note_id);

    IF count_all > 1 THEN
        RAISE EXCEPTION 'Invalid orders';
    ELSE
        RETURN NEW;
    END IF;
END

$$ LANGUAGE plpgsql;

GO

CREATE TRIGGER verify_unique_order
    AFTER INSERT OR UPDATE
    ON note_blocks
    FOR EACH ROW
EXECUTE PROCEDURE note_block_order_uniques();

GO