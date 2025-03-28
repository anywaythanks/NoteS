package com.notes.services.managers;

import com.notes.exceptions.NoteNotFoundException;
import com.notes.exceptions.TagNotFoundException;
import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.mappers.repository.PageRepositoryMapper;
import com.notes.models.domain.NoteContentDomainDto;
import com.notes.models.domain.NoteFullDomainDto;
import com.notes.models.domain.NotePartialDomainDto;
import com.notes.models.domain.NoteTagsDomainDto;
import com.notes.models.domain.PageDomainDto;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteDto;
import com.notes.models.entity.NoteTagRef;
import com.notes.models.entity.Tag;
import com.notes.repository.NoteRepository;
import com.notes.repository.NoteRepositoryDb;
import com.notes.repository.NoteTagRefRepository;
import com.notes.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteInformationService {
   private final AccountInformationService accountInformationService;
   private final NoteRepository noteRepository;
   private final NoteRepositoryDb noteRepositoryDb;
   private final NoteTagRefRepository noteTagRefRepository;
   private final PageRepositoryMapper pageMapper;
   private final NoteRepositoryMapper noteMapper;
   private final TagRepository tagRepository;

   public PageDomainDto<NoteTagsDomainDto> findByTitle(String title, String nameOwner, Integer page, Integer limit) {
      var account = accountInformationService.findAccount(nameOwner);
      var list = noteRepository
              .searchByTitle(title, account.id(),
                      PageRequest.of(page, limit))
              .map(noteMapper::of);
      return pageMapper.of(loadTags(list));
   }

   public PageDomainDto<NoteTagsDomainDto> semanticSearch(String query, String nameOwner, Integer page, Integer limit) {
      var account = accountInformationService.findAccount(nameOwner);
      var list = noteRepository
              .semanticSearch(query, account.id(),
                      PageRequest.of(page, limit))
              .map(noteMapper::of);
      return pageMapper.of(loadTags(list));
   }

   public PageDomainDto<NoteTagsDomainDto> findByOwner(String nameOwner, Integer page, Integer limit) {
      var account = accountInformationService.findAccount(nameOwner);
      var list = noteRepositoryDb
              .findNotesByOwner(account.id(),
                      PageRequest.of(page, limit))
              .map(noteMapper::of);
      return pageMapper.of(loadTags(list));
   }

   @PostAuthorize("returnObject.owner.name == nameOwner")
   public NotePartialDomainDto findByPath(String path, String nameOwner) {
      return unsafeFindByPath(path);
   }

   @PostAuthorize("returnObject.owner.name == nameOwner || returnObject.isPublic")
   public NotePartialDomainDto findPublicByPath(String path, String nameOwner) {
      return unsafeFindByPath(path);
   }

   public NotePartialDomainDto unsafeFindByPath(String path) {
      Note note = noteRepositoryDb.findByPath(path).orElseThrow(NoteNotFoundException::new);
      return noteMapper.of(note);
   }


   @PostAuthorize("returnObject.owner.name == nameOwner || returnObject.isPublic")
   public NoteContentDomainDto findPublicContentByPath(String path, String nameOwner) {
      NoteDto noteDto = noteRepository.findByPath(path).orElseThrow(NoteNotFoundException::new);
      return noteMapper.of(noteDto);
   }

   @PostAuthorize("returnObject.owner.name == nameOwner || returnObject.isPublic")
   public NoteFullDomainDto fullFindPublicByPath(String path, String nameOwner) {
      var partial = findPublicContentByPath(path, nameOwner);
      return loadTags(partial);
   }

   public NoteFullDomainDto unsafeFullFindByPath(String path) {
      var note = noteRepository.findByPath(path).orElseThrow(NoteNotFoundException::new);
      return loadTags(noteMapper.of(note));
   }

   public NoteFullDomainDto loadTags(NoteContentDomainDto note) {
      var tags = noteTagRefRepository.findByNoteId(note.id());
      return noteMapper.of(note, tags);
   }

   public PageDomainDto<NoteTagsDomainDto> findByTags(List<String> tags, List<String> filters, String ownerName,
                                                      boolean isStrong, Integer limit, Integer page) {
      var account = accountInformationService.findAccount(ownerName);
      var tagIds = tagRepository.getTags(tags, account.id()).stream().map(Tag::getId).toList();
      var filterIds = tagRepository.getTags(filters, account.id()).stream().map(Tag::getId).toList();
      if (tagIds.size() != tags.size() || filterIds.size() != filters.size()) throw new TagNotFoundException();
      var notes = isStrong ?
              noteRepositoryDb.findStrongByTagId(tagIds, filterIds, account.id(), PageRequest.of(page, limit)) :
              noteRepositoryDb.findWeakByTagId(tagIds, filterIds, account.id(), PageRequest.of(page, limit));
      var noteTags = loadTags(notes.map(noteMapper::of));
      return pageMapper.of(noteTags);
   }

   private Page<NoteTagsDomainDto> loadTags(Page<NotePartialDomainDto> notes) {
      var noteIds = notes.stream().map(NotePartialDomainDto::id).toList();
      var tags = noteTagRefRepository.findByNoteIds(noteIds)
              .stream().collect(Collectors.groupingBy(
                      ntr -> ntr.getNote().getId(),
                      Collectors.mapping(NoteTagRef::getTag, Collectors.toList())));
      return notes.map(note -> noteMapper.of(note, tags.get(note.id())));
   }
}
