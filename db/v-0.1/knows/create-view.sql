CREATE VIEW knows
            (account_id, account_name, note_id, note_title, note_block_id, block_content, syntax_id, block_links,
             block_subblocks) AS
SELECT accounts.id,
       accounts.name,
       notes.id,
       public.notes.title,
       public.note_blocks.id,
       public.type_custom.content,
       public.type_custom.syntax_types_id,
       type_links.link_block_id,
       type_subblocks.block_id
from accounts
         Left JOIN public.notes
                   on accounts.id = public.notes.account_id
         LEFT JOIN public.note_blocks
                   on notes.id = public.note_blocks.note_id
         LEFT OUTER JOIN public.type_custom
                         on note_blocks.id = public.type_custom.block_id
         LEFT OUTER JOIN public.type_links
                         on note_blocks.id = public.type_links.block_id
         LEFT OUTER JOIN public.type_subblocks
                         on note_blocks.id = public.type_subblocks.block_id
order by (note_id, "order")

GO