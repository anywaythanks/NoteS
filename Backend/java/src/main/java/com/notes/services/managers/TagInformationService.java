package com.notes.services.managers;

import com.notes.exceptions.TagNotFoundException;
import com.notes.mappers.repository.TagRepositoryMapper;
import com.notes.models.domain.TagDomainDto;
import com.notes.repository.NoteTagRefRepository;
import com.notes.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagInformationService {
   private final AccountInformationService accountInformationService;
   private final NoteInformationService noteInformationService;
   private final TagRepository tagRepository;
   private final NoteTagRefRepository noteTagRefRepository;
   private final TagRepositoryMapper tagRepositoryMapper;

   public List<TagDomainDto> findTags(String accountName) {
      var account = accountInformationService.findAccount(accountName);
      var tags = tagRepository.findByOwner(account.id());
      return tags.stream().map(tagRepositoryMapper::of).toList();
   }

   public List<TagDomainDto> findTags(String notePath, String accountName) {
      var note = noteInformationService.findByPath(notePath, accountName);
      var tags = noteTagRefRepository.findByNoteId(note.id());
      return tags.stream().map(tagRepositoryMapper::of).toList();
   }

   public TagDomainDto getTag(String accountName, String tagName) {
      var account = accountInformationService.findAccount(accountName);
      var tag = tagRepository.find(account.id(), tagName).orElseThrow(TagNotFoundException::new);
      return tagRepositoryMapper.of(tag);
   }
}
