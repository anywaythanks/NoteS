package com.notes.controllers;

import com.notes.models.dto.account.AccName;
import com.notes.models.dto.note.NoteCreateResponseDto;
import com.notes.models.dto.note.NoteEditContentResponseDto;
import com.notes.models.dto.note.NoteEditOnlyContentRequestDto;
import com.notes.models.dto.note.NoteEditOtherRequestDto;
import com.notes.models.dto.note.NoteEditOtherResponseDto;
import com.notes.models.dto.note.NoteEditPublicRequestDto;
import com.notes.models.dto.note.NoteEditPublicResponseDto;
import com.notes.models.dto.note.NotePath;
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
   private final AccountRegisterService register;
   private final NoteInformationService noteInformationService;
   private final NoteEditService editService;

   @PatchMapping(path = "/{pathNote}/publish", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_ALL_NOTES', 'SET_ALL_PUBLIC_STATUS_NOTES')")
   public NoteEditPublicResponseDto EditPublicAllNote(@Valid @PathVariable AccName accountName,
                                                      @Valid @PathVariable NotePath pathNote,
                                                      @Valid @RequestBody NoteEditPublicRequestDto editDto) {

      //        Check(accountName);//TODO: В сервисах стоит сделать проверку через principal == uuid
//
//        return editService.PublishNote(pathNote, editDto);
      return null;
   }

   @PatchMapping(path = "/{pathNote}/content", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_ALL_NOTES', 'EDIT_ALL_NOTES')")
   public NoteEditOtherResponseDto EditNoteAll(@Valid @PathVariable AccName accountName,
                                               @Valid @PathVariable NotePath pathNote,
                                               @Valid @RequestBody NoteEditOtherRequestDto editDto) {
//        Check(accountName);
//
//        return editService.EditNote(pathNote, editDto);
   }

   @PostMapping(path = "/{pathNote}/content", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES', 'EDIT_OWN_NOTES')")
   public NoteEditContentResponseDto EditContentNote(@Valid @PathVariable AccName accountName,
                                                     @Valid @PathVariable NotePath pathNote,
                                                     @Valid @RequestBody NoteEditOnlyContentRequestDto editDto) {
//        Check(accountName);
//
//        return await editService.EditNote(pathNote, accountName, editDto);
   }

    [

   @GetMapping(path = "/{pathNote}", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_ALL_NOTES')")
   public NoteCreateResponseDto GetNoteAll(@Valid @PathVariable AccName accountName,
                                           @Valid @PathVariable NotePath pathNote) {
//      Check(accountName);
//
//      return await noteInformationService.GetFull(pathNote);
   }
}
