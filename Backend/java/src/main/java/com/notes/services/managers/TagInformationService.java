package com.notes.services.managers;

import com.notes.exceptions.TagNotFoundException;
import com.notes.mappers.repository.TagRepositoryMapper;
import com.notes.models.domain.TagDomainDto;
import com.notes.repository.NoteTagRefRepository;
import com.notes.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service providing tag retrieval operations and association lookups.
 * Handles tag search and relationship queries with proper access control.
 */
@Service
@RequiredArgsConstructor
public class TagInformationService {
   private final AccountInformationService accountInformationService;
   private final NoteInformationService noteInformationService;
   private final TagRepository tagRepository;
   private final NoteTagRefRepository noteTagRefRepository;
   private final TagRepositoryMapper tagRepositoryMapper;

   /**
    * Retrieves all tags belonging to an account.
    *
    * @param accountName Owner's account name
    * @return List of TagDomainDto for the account
    */
   public List<TagDomainDto> findTags(String accountName) {
      var account = accountInformationService.findAccount(accountName);
      var tags = tagRepository.findByOwner(account.id());
      return tags.stream().map(tagRepositoryMapper::of).toList();
   }

   /**
    * Retrieves tags associated with a specific note.
    *
    * @param notePath    Note's path identifier
    * @param accountName Owner's account name
    * @return List of TagDomainDto associated with the note
    */
   public List<TagDomainDto> findTags(String notePath, String accountName) {
      var note = noteInformationService.findByPath(notePath, accountName);
      var tags = noteTagRefRepository.findByNoteId(note.id());
      return tags.stream().map(tagRepositoryMapper::of).toList();
   }

   /**
    * Retrieves a specific tag by name for an account.
    *
    * @param accountName Owner's account name
    * @param tagName     Tag name to retrieve
    * @return TagDomainDto with tag details
    * @throws TagNotFoundException If tag doesn't exist
    */
   public TagDomainDto getTag(String accountName, String tagName) {
      var account = accountInformationService.findAccount(accountName);
      var tag = tagRepository.find(account.id(), tagName).orElseThrow(TagNotFoundException::new);
      return tagRepositoryMapper.of(tag);
   }
}
