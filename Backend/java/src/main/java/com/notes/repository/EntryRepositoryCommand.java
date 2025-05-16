package com.notes.repository;

import com.notes.models.entity.EntryCreateDto;
import com.notes.models.entity.EntryEditDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface EntryRepositoryCommand {
   void delete(Long noteId);

   void create(EntryCreateDto noteDto);

   void publish(Long noteId, boolean isPublic);

   void edit(Long noteId, EntryEditDto noteDto);
}
