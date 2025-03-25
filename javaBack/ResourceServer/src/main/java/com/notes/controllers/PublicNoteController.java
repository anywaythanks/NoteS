package com.notes.controllers;

import com.notes.models.dto.account.AccName;
import com.notes.models.dto.note.NoteCreateRequestDto;
import com.notes.models.dto.note.NoteEditContentResponseDto;
import com.notes.models.dto.note.NoteEditOnlyContentRequestDto;
import com.notes.models.dto.note.NoteEditOtherRequestDto;
import com.notes.models.dto.note.NoteEditOtherResponseDto;
import com.notes.models.dto.note.NoteEditPublicRequestDto;
import com.notes.models.dto.note.NoteEditPublicResponseDto;
import com.notes.models.dto.note.NotePath;
import com.notes.models.dto.note.NoteSearchContentResponseDto;
import com.notes.models.dto.note.NoteSearchRequestDto;
import com.notes.models.dto.note.NoteSemanticSearchRequestDto;
import com.notes.models.dto.page.LimitDto;
import com.notes.models.dto.page.PageDto;
import com.notes.models.dto.page.PageSizeDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/private/{accountName}/notes")
@RequiredArgsConstructor
public class PublicNoteController {
   private final AccountRegisterService register;
   private final NoteInformationService noteInformationService;
   private final TagInformationService tagInformationService;
   private final NoteEditService editService;

   @PatchMapping(path = "/{pathNote}/publish", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_ALL_NOTES', 'SET_ALL_PUBLIC_STATUS_NOTES')")
   public NoteEditPublicResponseDto EditPublicAllNote(@Valid @PathVariable AccName accountName,
                                                      @Valid @PathVariable NotePath pathNote,
                                                      @Valid @RequestBody NoteEditPublicRequestDto editDto) {
//        Check(accountName);
//
//        return editService.PublishNote(pathNote, editDto);
      return null;
   }

   @PostMapping(path = "/{pathNote}/publish", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES', 'SET_OWN_PUBLIC_STATUS_NOTES')")
   public NoteEditPublicResponseDto EditPublicNote(@Valid @PathVariable AccName accountName,
                                                   @Valid @PathVariable NotePath pathNote,
                                                   @Valid @RequestBody NoteEditPublicRequestDto editDto) {
//      Check(accountName);
//
//      return editService.PublishNote(pathNote, accountName, editDto);
   }

   @PostMapping(path = "/{pathNote}", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES', 'EDIT_OWN_NOTES')")
   public NoteEditOtherResponseDto EditNote(@Valid @PathVariable AccName accountName,
                                            @Valid @PathVariable NotePath pathNote,
                                            @Valid @RequestBody NoteEditOtherRequestDto editDto) {
//      Check(accountName);
//
//      return editService.EditNote(pathNote, accountName, editDto);
   }

   @PostMapping(path = "/{pathNote}/content", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES', 'EDIT_OWN_NOTES')")
   public Task<NoteEditContentResponseDto> EditContentNote(@Valid @PathVariable AccName accountName,
                                                           @Valid @PathVariable NotePath pathNote,
                                                           @Valid @RequestBody NoteEditOnlyContentRequestDto editDto) {
//      Check(accountName);
//
//      return await editService.EditNote(pathNote, accountName, editDto);
   }

   @PostMapping(headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES', 'EDIT_OWN_NOTES')")
   public CreatedResult CreateNote(@Valid @PathVariable AccName accountName,
                                   @Valid @RequestBody NoteCreateRequestDto editDto) {
//      Check(accountName);
//      NoteCreateResponseDto r = await editService.CreateNote(accountName, editDto);
//      return Created(Url.Action("GetNote", "PublicNote",
//              new { accountName.AccountName, pathNote = r.Path }, Request.Scheme), r);
   }

   @GetMapping(path = "/search/title", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES', 'SEARCH_OWN_NOTES')")
   public PageDto<NoteSearchContentResponseDto> SearchByTitleNotes(@Valid @PathVariable AccName accountName,
                                                                   @Valid @RequestAttribute PageSizeDto page,
                                                                   @Valid @RequestAttribute LimitDto limit,
                                                                   @Valid @RequestBody NoteSearchRequestDto noteSearch) {
//      Check(accountName);
//      var notes = await noteInformationService.Find(noteSearch, accountName, pagination, pagination);
//      return um.OfContent(notes);
   }

   @GetMapping(path = "/search/tag", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES', 'SEARCH_OWN_NOTES')")
   public PageDto<NoteSearchContentResponseDto> SearchByTagNotes(@Valid @PathVariable AccName accountName,
                                                                 @Valid @RequestAttribute("tag") List<String> tags,
                                                                 @Valid @RequestAttribute("filter") List<String> filterTags,
                                                                 @Valid @RequestAttribute PageSizeDto page,
                                                                 @Valid @RequestAttribute LimitDto limit,
                                                                 @Valid @RequestAttribute("and") boolean isAnd) {
//      Check(accountName);
//      var notes = tagInformationService.FindTags(
//              tm.Of(tags), tm.Of(filterTags),
//              accountName, isAnd, pagination, pagination);
//      return um.OfContent(notes);
   }

   @DeleteMapping(path = "/{pathNote}", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES', 'DELETE_NOTES')")
   public NoContentResult DelNote(@Valid @PathVariable AccName accountName, @Valid @PathVariable NotePath pathNote) {
//      Check(accountName);
//      await editService.Delete(pathNote, accountName);
//      return NoContent();
   }

   @GetMapping(path = "/search/semantic", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES', 'SEARCH_OWN_NOTES')")
   public PageDto<NoteSearchContentResponseDto> SemanticSearchNotes(@Valid @PathVariable AccName accountName,
                                                                    @Valid @RequestBody NoteSemanticSearchRequestDto noteSearch,
                                                                    @Valid @RequestAttribute PageSizeDto page,
                                                                    @Valid @RequestAttribute LimitDto limit,) {
//      Check(accountName);
//      var notes = await noteInformationService.FindSemantic(accountName, noteSearch, pagination, pagination);
//      return um.OfContent(notes);
   }

   @GetMapping(headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES')")
   public PageDto<NoteSearchContentResponseDto> Notes(@Valid @PathVariable AccName accountName,
                                                      @Valid @RequestAttribute PageSizeDto page,
                                                      @Valid @RequestAttribute LimitDto limit) {
//      Check(accountName);
//      var notes = noteInformationService.Find(accountName, pagination, pagination);
//      return um.OfContent(notes);
   }

   @GetMapping(path = "/{pathNote}", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES')")
   public NoteSearchContentResponseDto GetNote(@Valid @PathVariable AccName accountName,
                                               @Valid @PathVariable NotePath pathNote) {
//      Check(accountName);
//      var notes = await noteInformationService.GetFullPublic(pathNote, accountName);
//      return um.OfContentSearch(notes);
   }
}
