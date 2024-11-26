INSERT INTO accounts (id, uid, name, modified_by, created_on)
VALUES (1, '9c534ed5-5c79-4c7b-8535-080ef1bb956e', 'anton', now(), now());
INSERT INTO custom_types_syntax (id, syntax)
VALUES (1, 'plain');
INSERT INTO custom_types_syntax (id, syntax)
VALUES (2, 'latex');
INSERT INTO custom_types_syntax (id, syntax)
VALUES (3, 'md');

INSERT INTO notes (id, title, account_id, modified_by, created_on)
VALUES (1, 'Синхронизация', 1, now(), now());

INSERT INTO notes (id, title, account_id, modified_by, created_on)
VALUES (2, 'Блокировка', 1, now(), now());
BEGIN;--Мб order_seq сделать
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (1, 1, 0, now(), now());
INSERT INTO type_custom (block_id, content, syntax_types_id)
VALUES (1, 'Синхронизацией можно считать линеаризацию действий.', 1);
END;
BEGIN;
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (2, 1, 1, now(), now());
INSERT INTO type_custom (block_id, content, syntax_types_id)
VALUES (2, 'f(x) = x^2', 2);
END;
BEGIN;--Можно убрать лишний маппинг
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (3, 2, 0, now(), now());
INSERT INTO type_links (block_id, link_block_id)
VALUES (3, 1);
INSERT INTO type_custom (block_id, content, syntax_types_id)
VALUES (3, 'Синхронизация...', 1);
END;

INSERT INTO accounts (id, uid, name, modified_by, created_on)
VALUES (4, 'f8757bb0-3709-4c6d-80d3-53ac200d6413', 'user2', now(), now());
INSERT INTO notes (id, title, account_id, modified_by, created_on)
VALUES (10, 'Заметка1 юзера1', 4, now(), now());
INSERT INTO notes (id, title, account_id, modified_by, created_on)
VALUES (11, 'Заметка2 юзера2', 4, now(), now());
INSERT INTO notes (id, title, account_id, modified_by, created_on)
VALUES (12, 'Заметка3 юзера3', 4, now(), now());
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (10, 10, 0, now(), now());
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (11, 11, 0, now(), now());
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (12, 12, 0, now(), now());
INSERT INTO type_custom (block_id, content, syntax_types_id)
VALUES (10, '...', 1);
INSERT INTO type_custom (block_id, content, syntax_types_id)
VALUES (11, '...', 1);
INSERT INTO type_custom (block_id, content, syntax_types_id)
VALUES (12, '...', 1);
INSERT INTO type_subblocks (block_id)
VALUES (12);
INSERT INTO type_links (block_id, link_block_id)
VALUES (12, 11);

INSERT INTO accounts (id, uid, name, modified_by, created_on)
VALUES (1001, 'user123456', 'John Doe', '2024-06-16 17:35:00+02', '2024-06-16 10:00:00+02'),
       (1002, 'another_user', 'Jane Smith', '2024-06-15 14:20:00+02', '2024-06-14 12:00:00+02');

INSERT INTO notes (id, title, account_id, modified_by, created_on)
VALUES (1001, 'Test 1', 1001, now(), now());
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (1001, 1001, 0, '2024-06-16 17:35:00+02', '2024-06-16 10:00:00+02'),
       (1002, 1001, 1, '2024-06-16 17:40:00+02', '2024-06-16 10:00:00+02'),
       (1003, 1001, 2, '2024-06-16 17:35:00+02', '2024-06-14 12:00:00+02');
INSERT INTO type_custom (block_id, content, syntax_types_id)
VALUES (1001, 'This is a plain text note block.', 1), -- Plain text (ID 1)
       (1002, '# This is a heading\n* This is a bulleted list item', 3), -- Markdown (ID 3)
       (1003, '\\sqrt{2} = 1.414...', 2); -- Latex (ID 2)
INSERT INTO tags (id, name) VALUES (1, 'tag1');
INSERT INTO tags_notes_map (id_tag, id_note) VALUES (1, 1001);
INSERT INTO tags_notes_map (id_tag, id_note) VALUES (1, 11);
