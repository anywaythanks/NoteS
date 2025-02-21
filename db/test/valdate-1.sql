INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (100, 2, 2, now(), now());--OK
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (101, 2, 1, now(), now());--OK

INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (102, 2, 2, now(), now());--ERROR

INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (103, 2, 3, now(), now());--OK
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (104, 2, 4, now(), now());--OK
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (105, 2, 5, now(), now());--OK
UPDATE note_blocks
SET "order" = 0
WHERE note_id = 2; --ERROR

create function search(regex text, syntax_custom_type text) returns int
    language plpgsql
as
$$
DECLARE
    res  text;
    id_i bigint;
    cr1  refcursor;
BEGIN
    open cr1 for
        select id, concat_note(title, syntax_custom_type) from notes;
    LOOP
        FETCH cr1 INTO id_i;
        FETCH cr1 INTO res;
        IF NOT FOUND THEN EXIT; END IF;
        IF res similar to regex THEN return id_i; END IF;
    END LOOP;
    return res;
END;

$$;



select search('[0-9]', 'plain'),search('.', 'plain')