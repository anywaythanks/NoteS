package com.notes.services.managers;

import com.notes.exceptions.TagUniqueException;
import com.notes.mappers.repository.TagRepositoryMapper;
import com.notes.models.domain.TagCreateDto;
import com.notes.models.domain.TagDomainDto;
import com.notes.models.entity.Account;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteTagRef;
import com.notes.models.entity.Tag;
import com.notes.repository.NoteTagRefRepository;
import com.notes.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handling tag management operations including creation, assignment, and removal.
 * Enforces tag uniqueness and manages note-tag relationships.
 */
@Service
@RequiredArgsConstructor
public class TagEditService {
   private final AccountInformationService accountInformationService;
   private final NoteInformationService noteInformationService;
   private final TagRepository tagRepository;
   private final NoteTagRefRepository noteTagRefRepository;
   private final TagInformationService tagInformationService;
   private final TagRepositoryMapper tagRepositoryMapper;

   /**
    * Removes a tag association from a note.
    *
    * @param pathNote    Note's path identifier
    * @param accountName Owner's account name
    * @param tag         Tag name to remove
    * @throws com.notes.exceptions.TagNotFoundException If tag doesn't exist
    */
   public void delete(String pathNote, String accountName, String tag) {
      var note = noteInformationService.findPublicByPath(pathNote, accountName);
      var tagI = tagInformationService.getTag(accountName, tag);
      noteTagRefRepository.deleteById(new NoteTagRef.NoteTagId(note.id(), tagI.id()));
   }

   /**
    * Adds a tag association to a note.
    *
    * @param pathNote    Note's path identifier
    * @param accountName Owner's account name
    * @param tag         Tag name to add
    * @throws TagUniqueException If tag already exists on note
    */
   public void add(String pathNote, String accountName, String tag) {
      var note = noteInformationService.findPublicByPath(pathNote, accountName);
      var tagI = tagInformationService.getTag(accountName, tag);
      var ntr = noteTagRefRepository.findById(new NoteTagRef.NoteTagId(note.id(), tagI.id()));
      if(ntr.isPresent()) throw new TagUniqueException();
      noteTagRefRepository.save(NoteTagRef
              .builder()
              .id(new NoteTagRef.NoteTagId(note.id(), tagI.id()))
              .build());
   }

   /**
    * Creates a new tag for an account.
    *
    * @param accountName Owner's account name
    * @param tagDto      Tag creation data
    * @return Created TagDomainDto
    * @throws TagUniqueException If tag name already exists for account
    */
   public TagDomainDto create(String accountName, TagCreateDto tagDto) {
      var acc = accountInformationService.findAccount(accountName);
      if(tagRepository.find(acc.id(), tagDto.name()).isPresent()) throw new TagUniqueException();
      var tag = tagRepository.save(Tag.builder()
              .color(tagDto.color())
              .owner(Account.builder().id(acc.id()).build())
              .name(tagDto.name())
              .build());
      return tagRepositoryMapper.of(tag);
   }
}
