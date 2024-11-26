CREATE PROCEDURE update_note_block(block_id bigint)
    LANGUAGE SQL
AS
$$
UPDATE note_blocks
SET modified_by=now()
WHERE id = block_id;
$$;

GO

CREATE PROCEDURE replace_content(block_id bigint, new_content text, syntax_id int default null)
    LANGUAGE plpgsql
AS
$$
DECLARE
    old_content bigint;
BEGIN
    old_content := (select type_custom.block_id from type_custom where type_custom.block_id = replace_content.block_id);
    IF old_content IS NULL THEN
        INSERT INTO type_custom (block_id, content, syntax_types_id)
        VALUES (replace_content.block_id, new_content, syntax_id);
    ELSE
        IF syntax_id IS NULL THEN
            UPDATE type_custom SET content=new_content WHERE type_custom.block_id = replace_content.block_id;
        ELSE
            UPDATE type_custom
            SET content=new_content,
                syntax_types_id=syntax_id
            WHERE type_custom.block_id = replace_content.block_id;
        END IF;

    END IF;
    CALL update_note_block(block_id);
END;
$$;

GO

CREATE PROCEDURE mark_subblock(block_id bigint)
    LANGUAGE plpgsql
AS
$$
DECLARE
    old_id bigint;
BEGIN
    old_id =
            (select type_subblocks.block_id from type_subblocks where type_subblocks.block_id = mark_subblock.block_id);
    IF old_id IS NULL THEN
        BEGIN
            INSERT INTO type_subblocks (block_id) VALUES (mark_subblock.block_id);
            CALL update_note_block(block_id);
        END;
    END IF;
END;
$$;

GO

CREATE PROCEDURE link_block(from_block bigint, to_block bigint)
    LANGUAGE plpgsql
AS
$$
DECLARE
    old_id bigint;
BEGIN
    old_id = (select block_id from type_links where block_id = from_block);
    IF old_id IS NULL THEN
        INSERT INTO type_links (block_id, link_block_id) VALUES (from_block, to_block);
    ELSE
        UPDATE type_links SET link_block_id=to_block WHERE block_id = from_block;
    END IF;
    CALL update_note_block(from_block);
END;
$$;

GO