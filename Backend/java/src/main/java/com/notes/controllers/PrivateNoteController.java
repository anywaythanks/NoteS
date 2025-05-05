package com.notes.controllers;

import com.notes.mappers.request.NoteRequestMapper;
import com.notes.mappers.response.NoteResponseMapper;
import com.notes.models.api.account.AccName;
import com.notes.models.api.note.NoteEditOtherResponseDto;
import com.notes.models.api.note.NoteEditPublicRequestDto;
import com.notes.models.api.note.NoteEditPublicResponseDto;
import com.notes.models.api.note.NoteEditRequestDto;
import com.notes.models.api.note.NoteFullResponseDto;
import com.notes.models.api.note.NotePath;
import com.notes.services.managers.NoteEditService;
import com.notes.services.managers.NoteInformationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PrivateNote/patch_api_private__accountName__notes__pathNote__publish">Click</a>
    */
   @PatchMapping(path = "/{pathNote}/publish", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-all-notes', 'set-all-public-status-notes')")
   public void editPublicAllNote(@Valid @PathVariable("accountName") AccName accountName,//TODO: не используется
                                                      @Valid @PathVariable NotePath pathNote,
                                                      @Valid @RequestBody NoteEditPublicRequestDto editDto) {
      editService.unsafePublishNote(pathNote.path(), noteRequestMapper.of(editDto));
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PrivateNote/patch_api_private__accountName__notes__pathNote__content">Click</a>
    */
   @PatchMapping(path = "/{pathNote}/content", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-all-notes', 'edit-all-notes')")
   public void editNoteAll(@Valid @PathVariable("accountName") AccName accountName,//TODO: не используется
                                               @Valid @PathVariable NotePath pathNote,
                                               @Valid @RequestBody NoteEditRequestDto editDto) {
      editService.unsafeEditNote(pathNote.path(), noteRequestMapper.of(editDto));
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PrivateNote/get_api_private__accountName__notes__pathNote_">Click</a>
    */
   @GetMapping(path = "/{pathNote}")
   @PreAuthorize("hasAnyAuthority('read-all-notes')")
   public NoteFullResponseDto getNoteAll(@Valid @PathVariable("accountName") AccName accountName,//TODO: не используется
                                         @Valid @PathVariable NotePath pathNote) {
      return noteResponseMapper.ofFull(noteInformationService.unsafeFullFindByPath(pathNote.path()));
   }
}
