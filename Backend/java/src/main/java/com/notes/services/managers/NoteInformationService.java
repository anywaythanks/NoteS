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

/**
 * Comprehensive service for note retrieval operations.
 * Handles note searching, filtering, and security validation across multiple data sources.
 */
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

   /**
    * Searches notes by title with tag loading and pagination.
    *
    * @param title     Search query for note titles
    * @param nameOwner Account name of the note owner
    * @param page      Pagination page number (0-based)
    * @param limit     Number of results per page
    * @return PageDomainDto of NoteTagsDomainDto with loaded tags
    */
   public PageDomainDto<NoteTagsDomainDto> findByTitle(String title, String nameOwner, Integer page, Integer limit) {
      var account = accountInformationService.findAccount(nameOwner);
      var list = noteRepository
              .searchByTitle(title, account.id(),
                      PageRequest.of(page, limit))
              .map(noteMapper::of);
      return pageMapper.of(loadTags(list));
   }

   /**
    * Performs semantic search using vector embeddings with tag loading.
    *
    * @param query     Natural language search query
    * @param nameOwner Account name of the note owner
    * @param page      Pagination page number (0-based)
    * @param limit     Number of results per page
    * @return PageDomainDto of NoteTagsDomainDto with loaded tags
    */
   public PageDomainDto<NoteTagsDomainDto> semanticSearch(String query, String nameOwner, Integer page, Integer limit) {
      var account = accountInformationService.findAccount(nameOwner);
      var list = noteRepository
              .semanticSearch(query, account.id(),
                      PageRequest.of(page, limit))
              .map(noteMapper::of);
      return pageMapper.of(loadTags(list));
   }

   /**
    * Retrieves paginated notes by owner with tag loading.
    *
    * @param nameOwner Account name of the note owner
    * @param page      Pagination page number (0-based)
    * @param limit     Number of results per page
    * @return PageDomainDto of NoteTagsDomainDto with loaded tags
    */
   public PageDomainDto<NoteTagsDomainDto> findByOwner(String nameOwner, Integer page, Integer limit) {
      var account = accountInformationService.findAccount(nameOwner);
      var list = noteRepositoryDb
              .findNotesByOwner(account.id(),
                      PageRequest.of(page, limit))
              .map(noteMapper::of);
      return pageMapper.of(loadTags(list));
   }

   /**
    * Finds note by path with ownership validation.
    *
    * @param path      Note's unique path identifier
    * @param nameOwner Account name of the requester
    * @return NotePartialDomainDto with basic note details
    * @PostAuthorize Ensures requester is the note owner
    */
   @PostAuthorize("returnObject.owner.name == #nameOwner")
   public NotePartialDomainDto findByPath(String path, String nameOwner) {
      return unsafeFindByPath(path);
   }

   /**
    * Finds public note by path with access validation.
    *
    * @param path      Note's unique path identifier
    * @param nameOwner Account name of the requester
    * @return NotePartialDomainDto with basic note details
    * @PostAuthorize Allows access if public or owner matches requester
    */
   @PostAuthorize("returnObject.owner.name == #nameOwner || returnObject.isPublic")
   public NotePartialDomainDto findPublicByPath(String path, String nameOwner) {
      return unsafeFindByPath(path);
   }

   /**
    * Finds note by path with without validation.
    *
    * @param path Note's unique path identifier
    * @return NotePartialDomainDto with basic note details
    */
   public NotePartialDomainDto unsafeFindByPath(String path) {
      Note note = noteRepositoryDb.findByPath(path).orElseThrow(NoteNotFoundException::new);
      return noteMapper.of(note);
   }

   /**
    * Retrieves full public note content with validation.
    *
    * @param path      Note's unique path identifier
    * @param nameOwner Account name of the requester
    * @return NoteContentDomainDto with full content details
    * @PostAuthorize Allows access if public or owner matches requester
    */
   @PostAuthorize("returnObject.owner.name == #nameOwner || returnObject.isPublic")
   public NoteContentDomainDto findPublicContentByPath(String path, String nameOwner) {
      NoteDto noteDto = noteRepository.findByPath(path).orElseThrow(NoteNotFoundException::new);
      return noteMapper.of(noteDto);
   }

   /**
    * Retrieves full public note content and tags with validation.
    *
    * @param path      Note's unique path identifier
    * @param nameOwner Account name of the requester
    * @return NoteContentDomainDto with full content details
    * @PostAuthorize Allows access if public or owner matches requester
    */
   @PostAuthorize("returnObject.owner.name == #nameOwner || returnObject.isPublic")
   public NoteFullDomainDto fullFindPublicByPath(String path, String nameOwner) {
      var partial = findPublicContentByPath(path, nameOwner);
      return loadTags(partial);
   }

   /**
    * Retrieves full public note content and tags without validation.
    *
    * @param path Note's unique path identifier
    * @return NoteContentDomainDto with full content details
    * @PostAuthorize Allows access if public or owner matches requester
    */
   public NoteFullDomainDto unsafeFullFindByPath(String path) {
      var note = noteRepository.findByPath(path).orElseThrow(NoteNotFoundException::new);
      return loadTags(noteMapper.of(note));
   }

   /**
    * Internal method to load tags for a single note.
    *
    * @param note NoteContentDomainDto to enrich with tags
    * @return NoteFullDomainDto with associated tags
    */
   public NoteFullDomainDto loadTags(NoteContentDomainDto note) {
      var tags = noteTagRefRepository.findByNoteId(note.id());
      return noteMapper.of(note, tags);
   }

   /**
    * Finds notes by tags with filtering options.
    *
    * @param tags      List of required tag names
    * @param filters   List of excluded tag names
    * @param ownerName Account name of the note owner
    * @param isStrong  Flag for strict tag matching (all vs any)
    * @param limit     Number of results per page
    * @param page      Pagination page number (0-based)
    * @return PageDomainDto of filtered NoteTagsDomainDto
    * @throws TagNotFoundException if any specified tags are invalid
    */
   public PageDomainDto<NoteTagsDomainDto> findByTags(List<String> tags, List<String> filters, String ownerName,
                                                      boolean isStrong, Integer limit, Integer page) {
      var account = accountInformationService.findAccount(ownerName);
      var tagIds = tagRepository.getTags(tags, account.id()).stream().map(Tag::getId).toList();
      var filterIds = tagRepository.getTags(filters, account.id()).stream().map(Tag::getId).toList();
      if(tagIds.size() != tags.size() || filterIds.size() != filters.size()) throw new TagNotFoundException();
      var notes = isStrong ?
              noteRepositoryDb.findStrongByTagId(tagIds, filterIds, account.id(), PageRequest.of(page, limit)) :
              noteRepositoryDb.findWeakByTagId(tagIds, filterIds, account.id(), PageRequest.of(page, limit));
      var noteTags = loadTags(notes.map(noteMapper::of));
      return pageMapper.of(noteTags);
   }

   /**
    * Internal method to load tags for paginated results.
    *
    * @param notes Page of NotePartialDomainDto to enrich
    * @return Page of NoteTagsDomainDto with associated tags
    */
   private Page<NoteTagsDomainDto> loadTags(Page<NotePartialDomainDto> notes) {
      var noteIds = notes.stream().map(NotePartialDomainDto::id).toList();
      var tags = noteTagRefRepository.findByNoteIds(noteIds)
              .stream().collect(Collectors.groupingBy(
                      ntr -> ntr.getNote().getId(),
                      Collectors.mapping(NoteTagRef::getTag, Collectors.toList())));
      return notes.map(note -> noteMapper.of(note, tags.get(note.id())));
   }
}
