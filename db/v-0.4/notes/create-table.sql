alter table if exists notes
    add type int not null;

GO

alter table if exists notes
    add prev int not null;

GO

alter table if exists notes
    add constraint FK_Notes_Notes_Prev
        foreign key (prev)
            references notes

GO

alter table if exists notes
    add constraint FK_Notes_Note_Type
        foreign key (type)
            references note_types

GO

CREATE FUNCTION defaulter_prev_notes()
    RETURNS trigger AS
$$
BEGIN
    IF NEW.prev IS NULL THEN
        NEW.prev := NEW.id;
    END IF;
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

GO

CREATE TRIGGER trigger_defaulter_prev_notes
    BEFORE INSERT
    ON notes
    FOR EACH ROW
EXECUTE PROCEDURE defaulter_prev_notes();

GO