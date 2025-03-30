package com.notes.controllers;

import com.notes.mappers.request.NoteRequestMapper;
import com.notes.mappers.response.NoteResponseMapper;
import com.notes.models.api.account.AccName;
import com.notes.models.api.note.NoteEditContentResponseDto;
import com.notes.models.api.note.NoteEditOnlyContentRequestDto;
import com.notes.models.api.note.NoteEditOtherRequestDto;
import com.notes.models.api.note.NoteEditOtherResponseDto;
import com.notes.models.api.note.NoteEditPublicRequestDto;
import com.notes.models.api.note.NoteEditPublicResponseDto;
import com.notes.models.api.note.NotePath;
import com.notes.models.api.note.NoteSearchContentResponseDto;
import com.notes.services.managers.NoteEditService;
import com.notes.services.managers.NoteInformationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/{accountName}/notes")
@RequiredArgsConstructor
public class PrivateNoteController {
   private final NoteInformationService noteInformationService;
   private final NoteEditService editService;
   private final NoteRequestMapper noteRequestMapper;
   private final NoteResponseMapper noteResponseMapper;

   @PatchMapping(path = "/{pathNote}/publish", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-all-notes', 'set-all-public-status-notes')")
   public NoteEditPublicResponseDto editPublicAllNote(@Valid @PathVariable AccName accountName,//TODO: не используется
                                                      @Valid @PathVariable NotePath pathNote,
                                                      @Valid @RequestBody NoteEditPublicRequestDto editDto) {
      return noteResponseMapper.ofPublic(editService.unsafePublishNote(pathNote.path(), noteRequestMapper.of(editDto)));
   }

   @PatchMapping(path = "/{pathNote}/content", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-all-notes', 'edit-all-notes')")
   public NoteEditOtherResponseDto editNoteAll(@Valid @PathVariable AccName accountName,//TODO: не используется
                                               @Valid @PathVariable NotePath pathNote,
                                               @Valid @RequestBody NoteEditOtherRequestDto editDto) {
      return noteResponseMapper.ofOther(editService.unsafeEditNote(pathNote.path(), noteRequestMapper.of(editDto)));
   }

   @PostMapping(path = "/{pathNote}/content", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-notes', 'edit-own-notes')")
   public NoteEditContentResponseDto editContentNote(@Valid @PathVariable AccName accountName,//TODO: не используется
                                                     @Valid @PathVariable NotePath pathNote,
                                                     @Valid @RequestBody NoteEditOnlyContentRequestDto editDto) {
      return noteResponseMapper.ofEditContent(editService.unsafeEditContentNote(pathNote.path(), noteRequestMapper.of(editDto)));
   }

   @GetMapping(path = "/{pathNote}")
   @PreAuthorize("hasAnyAuthority('read-all-notes')")
   public NoteSearchContentResponseDto getNoteAll(@Valid @PathVariable AccName accountName,//TODO: не используется
                                                  @Valid @PathVariable NotePath pathNote) {
      return noteResponseMapper.ofFull(noteInformationService.unsafeFullFindByPath(pathNote.path()));
   }
}
