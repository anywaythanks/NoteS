package com.notes.services.managers;

import com.notes.exceptions.TagNotFoundException;
import com.notes.exceptions.TagUniqueException;
import com.notes.mappers.repository.TagRepositoryMapper;
import com.notes.models.domain.TagCreateDto;
import com.notes.models.domain.TagDomainDto;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteTagRef;
import com.notes.models.entity.Tag;
import com.notes.repository.NoteTagRefRepository;
import com.notes.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagEditService {
   private final AccountInformationService accountInformationService;
   private final NoteInformationService noteInformationService;
   private final TagRepository tagRepository;
   private final NoteTagRefRepository noteTagRefRepository;
   private final TagInformationService tagInformationService;
   private final TagRepositoryMapper tagRepositoryMapper;

   public void delete(String pathNote, String accountName, String tag) {
      var note = noteInformationService.findPublicByPath(pathNote, accountName);
      var tagI = tagInformationService.getTag(accountName, tag);
      noteTagRefRepository.deleteById(new NoteTagRef.NoteTagId(note.id(), tagI.id()));
   }

   public void add(String pathNote, String accountName, String tag) {
      var note = noteInformationService.findPublicByPath(pathNote, accountName);
      var tagI = tagInformationService.getTag(accountName, tag);
      var ntr = noteTagRefRepository.findById(new NoteTagRef.NoteTagId(note.id(), tagI.id()));
      if (ntr.isPresent()) throw new TagUniqueException();
      noteTagRefRepository.save(NoteTagRef
              .builder()
              .tag(Tag.builder()
                      .id(tagI.id())
                      .build())
              .note(Note.builder()
                      .id(note.id())
                      .build())
              .build());
   }

   public TagDomainDto create(String accountName, TagCreateDto tagDto) {
      var acc = accountInformationService.findAccount(accountName);
      if (tagRepository.find(acc.id(), tagDto.name()).isPresent()) throw new TagUniqueException();
      var tag = tagRepository.save(Tag.builder().color(tagDto.color()).name(tagDto.name()).build());
      return tagRepositoryMapper.of(tag);
   }
}
