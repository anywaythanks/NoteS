INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (4, 2, 20, now(), now());
INSERT INTO type_custom (block_id, content, syntax_types_id)
VALUES (4, 'Это то, что синхронизирует...', 1);
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (5, 2, 40, now(), now());
INSERT INTO type_custom (block_id, content, syntax_types_id)
VALUES (5, 'А может и нет, хмхм', 1);
INSERT INTO note_blocks (id, note_id, "order", modified_by, created_on)
VALUES (6, 2, 30, now(), now());
INSERT INTO type_custom (block_id, content, syntax_types_id)
VALUES (6, 'Да точно!', 1);
--FETCH/CURSOR
select concat_note('Блокировка', 'plain');--<\text><\p><\text><\p>




CALL mark_subblock(101);
SELECT count(block_id)
from type_subblocks
where block_id = 101;--(>0)
CALL link_block(101, 103);
SELECT block_id, link_block_id
from type_links
where block_id = 101;--[101, 103]
CALL link_block(101, 101);--ERROR
CALL replace_content(101, 'test1', 1);
SELECT content, syntax_types_id
from type_custom
where block_id = 101;--['test1', 1]
CALL replace_content(101, 'test2');
SELECT content, syntax_types_id
from type_custom
where block_id = 101;--['test2', 1]
CALL replace_content(101, 'test3', 2);
SELECT content, syntax_types_id
from type_custom
where block_id = 101;--['test3', 2]
