package com.notes.repository;

import com.notes.models.entity.NoteCreateDto;
import com.notes.models.entity.NoteEditDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface NoteRepositoryCommand {
   void delete(Long noteId);

   void create(NoteCreateDto noteDto);

   void publish(Long noteId, boolean isPublic);

   void edit(Long noteId, NoteEditDto noteDto);
}
