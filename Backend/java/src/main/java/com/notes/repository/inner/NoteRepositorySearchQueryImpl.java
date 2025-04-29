package com.notes.repository.inner;

import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteContent;
import com.notes.models.entity.NoteScored;
import com.notes.repository.NoteRepositorySearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class NoteRepositorySearchQueryImpl implements NoteRepositorySearchQuery {
   private final NoteRepositoryDb db;
   private final NoteRepositoryElastic elastic;
   private final NoteRepositoryMapper noteRepositoryMapper;

   @Override
   public Page<NoteScored> semanticSearch(String query, Long ownerId, Pageable pageable) {
      var uuids = elastic.semanticSearch(query, ownerId, pageable);
      return map(uuids);
   }

   @Override
   public Page<NoteScored> searchByTitle(String title, Long ownerId, Pageable pageable) {
      var uuids = elastic.searchByTitle(title, ownerId, pageable);
      return map(uuids);
   }

   private Page<NoteScored> map(Page<NoteContent> page) {
      var uuids = page.stream()
              .map(NoteContent::getUuid)
              .toList();

      var result = db.findByElasticUuids(uuids);
      var r = StreamSupport.stream(result.spliterator(), false)
              .collect(Collectors.toMap(Note::getElasticUuid, Function.identity()));

      return page.map(noteContent -> noteRepositoryMapper.of(r.get(noteContent.getUuid()), noteContent));
   }
}
