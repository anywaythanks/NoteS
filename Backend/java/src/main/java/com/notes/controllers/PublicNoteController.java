package com.notes.controllers;

import com.notes.mappers.request.NoteRequestMapper;
import com.notes.mappers.response.NoteResponseMapper;
import com.notes.mappers.response.PageResponseMapper;
import com.notes.models.api.account.AccName;
import com.notes.models.api.note.EntrySearchTagsResponseDto;
import com.notes.models.api.note.NoteCreateRequestDto;
import com.notes.models.api.note.EntryCreateResponseDto;
import com.notes.models.api.note.NoteEditPublicRequestDto;
import com.notes.models.api.note.NoteEditRequestDto;
import com.notes.models.api.note.EntryFullResponseDto;
import com.notes.models.api.note.NotePath;
import com.notes.models.api.note.NoteSearchRequestDto;
import com.notes.models.api.note.NoteSemanticSearchRequestDto;
import com.notes.models.api.page.PageDto;
import com.notes.models.api.page.PageLimitDto;
import com.notes.models.api.page.PageSizeDto;
import com.notes.services.managers.NoteEditService;
import com.notes.services.managers.NoteInformationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api/public/{accountName}/notes")
@RequiredArgsConstructor
public class PublicNoteController {
   private final NoteInformationService noteInformationService;
   private final NoteEditService editService;
   private final NoteResponseMapper noteResponseMapper;
   private final NoteRequestMapper noteRequestMapper;
   private final PageResponseMapper pageResponseMapper;
   private final NoteEditService noteEditService;

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicNote/post_api_public__accountName__notes__pathNote__publish">Click</a>
    */
   @PostMapping(path = "/{pathNote}/publish", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-notes', 'set-own-public-status-notes')")
   public void editPublicNote(@Valid @PathVariable("accountName") AccName accountName,
                              @Valid @PathVariable NotePath pathNote,
                              @Valid @RequestBody NoteEditPublicRequestDto editDto) {
      editService.publishNote(pathNote.path(), accountName.name(), noteRequestMapper.of(editDto));
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicNote/post_api_public__accountName__notes__pathNote_">Click</a>
    */
   @PostMapping(path = "/{pathNote}", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-notes', 'edit-own-notes')")
   public void editNote(@Valid @PathVariable("accountName") AccName accountName,
                        @Valid @PathVariable NotePath pathNote,
                        @Valid @RequestBody NoteEditRequestDto editDto) {
      editService.editNote(pathNote.path(), accountName.name(), noteRequestMapper.of(editDto));
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicNote/get_api_public__accountName__notes__pathNote_">Click</a>
    */
   @PostMapping(headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-notes', 'edit-own-notes')")
   public ResponseEntity<EntryCreateResponseDto> createNote(@Valid @PathVariable("accountName") AccName accountName,
                                                            @Valid @RequestBody NoteCreateRequestDto editDto) {
      var note = editService.createNote(accountName.name(),
              noteRequestMapper.of(editDto));

      return ResponseEntity.created(ServletUriComponentsBuilder
                      .fromPath("{pathNote}")
                      .buildAndExpand(note.path()).toUri())
              .build();
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicNote/post_api_public__accountName__notes__pathNote__content">Click</a>
    */
   @GetMapping(path = "/search/title")
   @PreAuthorize("hasAnyAuthority('read-notes', 'search-own-notes')")
   public PageDto<EntrySearchTagsResponseDto> searchByTitleNotes(@Valid @PathVariable("accountName") AccName accountName,
                                                                 @Valid @RequestParam("Page") PageSizeDto page,
                                                                 @Valid @RequestParam("Limit") PageLimitDto limit,
                                                                 @Valid @RequestParam("Title") NoteSearchRequestDto noteSearch) {
      var notes = noteInformationService
              .findByTitle(noteSearch.title(), accountName.name(), page.page(), limit.limit())
              .map(noteResponseMapper::ofTags);

      return pageResponseMapper.of(notes);
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicNote/post_api_public__accountName__notes">Click</a>
    */
   @GetMapping(path = "/search/tag")
   @PreAuthorize("hasAnyAuthority('read-notes', 'search-own-notes')")
   public PageDto<EntrySearchTagsResponseDto> searchByTagNotes(@Valid @PathVariable("accountName") AccName accountName,
                                                               @Valid @RequestParam(value = "tag", defaultValue = "") String[] tags,
                                                               @Valid @RequestParam(value = "filter", defaultValue = "") String[] filterTags,
                                                               @Valid @RequestParam("Page") PageSizeDto page,
                                                               @Valid @RequestParam("Limit") PageLimitDto limit,
                                                               @Valid @RequestParam(value = "and", defaultValue = "true") boolean isAnd) {
      var notes = noteInformationService
              .findByTags(asList(tags), asList(filterTags), accountName.name(), isAnd, limit.limit(), page.page())
              .map(noteResponseMapper::ofTags);

      return pageResponseMapper.of(notes);
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicNote/get_api_public__accountName__notes">Click</a>
    */
   @GetMapping(path = "/search/semantic")
   @PreAuthorize("hasAnyAuthority('read-notes', 'search-own-notes')")
   public PageDto<EntrySearchTagsResponseDto> semanticSearchNotes(@Valid @PathVariable("accountName") AccName accountName,
                                                                  @Valid @RequestParam("Query") NoteSemanticSearchRequestDto noteSearch,
                                                                  @Valid @RequestParam("Page") PageSizeDto page,
                                                                  @Valid @RequestParam("Limit") PageLimitDto limit) {
      var notes = noteInformationService
              .semanticSearch(noteSearch.query(), accountName.name(), page.page(), limit.limit())
              .map(noteResponseMapper::ofTags);

      return pageResponseMapper.of(notes);
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicNote/get_api_public__accountName__notes_search_title">Click</a>
    */
   @GetMapping
   @PreAuthorize("hasAnyAuthority('read-notes')")
   public PageDto<EntrySearchTagsResponseDto> notes(@Valid @PathVariable("accountName") AccName accountName,
                                                    @Valid @RequestParam("Page") PageSizeDto page,
                                                    @Valid @RequestParam("Limit") PageLimitDto limit) {
      var notes = noteInformationService
              .findByOwner(accountName.name(), page.page(), limit.limit())
              .map(noteResponseMapper::ofTags);

      return pageResponseMapper.of(notes);
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicNote/get_api_public__accountName__notes_search_tag">Click</a>
    */
   @DeleteMapping(path = "/{pathNote}")
   @PreAuthorize("hasAnyAuthority('read-notes', 'delete-notes')")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delNote(@Valid @PathVariable AccName accountName, @Valid @PathVariable NotePath pathNote) {
      noteEditService.deleteNote(pathNote.path(), accountName.name());
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicNote/get_api_public__accountName__notes_search_semantic">Click</a>
    */
   @GetMapping(path = "/{pathNote}")
   @PreAuthorize("hasAnyAuthority('read-notes')")
   public EntryFullResponseDto getNote(@Valid @PathVariable("accountName") AccName accountName,
                                       @Valid @PathVariable NotePath pathNote) {
      var note = noteInformationService.fullFindPublicByPath(pathNote.path(), accountName.name());
      return noteResponseMapper.ofFull(note);
   }
}
