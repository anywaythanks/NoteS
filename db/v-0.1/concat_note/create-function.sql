CREATE FUNCTION concat_note(note_title text, syntax_custom_type text)
    RETURNS text
AS
$$
DECLARE
    cr2            refcursor;
    custom_type_id int;
    search_note_id bigint;
    cnt            text;
    res            text;
BEGIN
    custom_type_id := (select id from custom_types_syntax where syntax = syntax_custom_type);
    search_note_id := (select id from notes where title = note_title);
    open cr2 for
        select content
        from note_blocks
                 LEFT JOIN public.type_custom tc on note_blocks.id = tc.block_id
        WHERE note_id = search_note_id
          and syntax_types_id = custom_type_id;
    res = '';
    LOOP
        FETCH cr2 INTO cnt;
        IF NOT FOUND THEN EXIT; END IF;
        res = res || cnt || E'\n';
    END LOOP;
    return res;
END;
$$
    LANGUAGE 'plpgsql';

GO