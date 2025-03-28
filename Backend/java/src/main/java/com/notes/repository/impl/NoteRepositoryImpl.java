package com.notes.repository.impl;

import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteContent;
import com.notes.models.entity.NoteDto;
import com.notes.repository.NoteRepository;
import com.notes.repository.NoteRepositoryDb;
import com.notes.repository.NoteRepositoryElastic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//TODO: Контракт подразумевает, что мы (кто мы? я и Я?) инкапсулируем логику распределенных транзакций. Но ни паттерна saga, ни 2pc тут нет.
@Repository
@RequiredArgsConstructor
public class NoteRepositoryImpl implements NoteRepository {
   private final NoteRepositoryDb repositoryDb;
   private final NoteRepositoryElastic repositoryElastic;
   private final NoteRepositoryMapper noteRepositoryMapper;

   @Override
   public boolean delete(Note note) {
      repositoryDb.deleteById(note.getId());
      repositoryElastic.deleteById(note.getElasticUuid());
      return true;
   }

   @Override
   public NoteDto save(NoteDto noteDto) {
      var note = repositoryDb.save(noteDto.note());
      var content = repositoryElastic.save(noteDto.content());
      return noteRepositoryMapper.of(note, content);
   }

   @Override
   public Page<Note> searchByTitle(String title, Long ownerId, Pageable pageable) {
      var page = repositoryElastic.searchByTitle(title, ownerId, pageable);
      return map(page);
   }

   @Override
   public Page<Note> semanticSearch(String query, Long ownerId, Pageable pageable) {
      var page = repositoryElastic.semanticSearch(query, ownerId, pageable);
      return map(page);
   }

   @Override
   public Optional<NoteDto> findByPath(String path) {
      return repositoryDb.findByPath(path)
              .flatMap(note -> repositoryElastic.findById(note.getElasticUuid())
                      .map(e -> noteRepositoryMapper.of(note, e)));
   }

   @Override
   public Optional<NoteDto> findById(Long id) {
      return repositoryDb.findById(id)
              .flatMap(note -> repositoryElastic.findById(note.getElasticUuid())
                      .map(e -> noteRepositoryMapper.of(note, e)));
   }

   @Override
   public Page<NoteDto> findComments(Long noteId, Pageable pageable) {
      var page = repositoryDb.findComments(noteId, pageable);
      var list = repositoryElastic.findAllById(page.map(Note::getElasticUuid));
      var map = StreamSupport.stream(list.spliterator(), false)
              .collect(Collectors.toMap(NoteContent::getUuid, Function.identity()));
      return page.map(note -> noteRepositoryMapper.of(note, map.get(note.getElasticUuid())));
   }

   private Page<Note> map(Page<NoteContent> page) {
      var uuids = page.stream()
              .map(NoteContent::getUuid)
              .toList();

      var result = repositoryDb.findByElasticUuids(uuids);
      var r = StreamSupport.stream(result.spliterator(), false)
              .collect(Collectors.toMap(Note::getElasticUuid, Function.identity()));
      return page.map(noteContent -> r.get(noteContent.getUuid()));
   }
}
