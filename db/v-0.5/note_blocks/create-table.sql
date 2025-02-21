create table note_blocks
(
    id              bigint                      default nextval('note_blocks_seq'::regclass) not null
        primary key,
    note_id         bigint                                                                   not null
        constraint fk_noteblocks_note
            references notes,
    "order"         integer                                                                  not null
        constraint note_blocks_order_check
            check ("order" >= 0),
    modified_by     timestamp(6) with time zone default now()                                not null,
    created_on      timestamp(6) with time zone default now()                                not null,
    content         text                                                                     not null,
    syntax_types_id integer                                                                  not null
        constraint fk_noteblocks_customtypessyntax
            references custom_types_syntax
);

GO

CREATE TRIGGER trigger_update_modify_by_note_blocks
    BEFORE UPDATE
    ON note_blocks
    FOR EACH ROW
EXECUTE PROCEDURE updater_modify_by_note_blocks();
GO


CREATE TRIGGER verify_unique_order
    AFTER INSERT OR UPDATE
    ON note_blocks
    FOR EACH ROW
EXECUTE PROCEDURE note_block_order_uniques();

GO