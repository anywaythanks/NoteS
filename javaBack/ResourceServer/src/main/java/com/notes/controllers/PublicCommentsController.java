package com.notes.controllers;

import com.notes.models.dto.account.AccName;
import com.notes.models.dto.note.CommentCreateRequestDto;
import com.notes.models.dto.note.CommentCreateResponseDto;
import com.notes.models.dto.note.CommentEditRequestDto;
import com.notes.models.dto.note.CommentEditResponseDto;
import com.notes.models.dto.note.NotePath;
import com.notes.models.dto.note.NoteSearchContentResponseDto;
import com.notes.models.dto.page.LimitDto;
import com.notes.models.dto.page.PageDto;
import com.notes.models.dto.page.PageSizeDto;
import com.notes.services.managers.AccountRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/{accountName}/notes")
@RequiredArgsConstructor
public class PublicCommentsController {
   private final AccountRegisterService register;
   private final CommentInformationService commentInformationService;
   private final CommentEditService commentEditService;

   @GetMapping(path = "/notes/{pathNote}/comments", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_COMMENTS')")
   public PageDto<NoteSearchContentResponseDto> Comments(
           @Valid @PathVariable AccName accountName,
           @Valid @PathVariable NotePath pathNote,
           @Valid @RequestAttribute PageSizeDto page,
           @Valid @RequestAttribute LimitDto limit) {
//      Check(accountName);
//      var comments = await commentInformationService.Comments(accountName, pathNote, pagination, pagination);
//      return um.OfPage(comments);
   }

   @PostMapping(path = "/notes/{pathNote}/comments", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_COMMENTS', 'READ_NOTES', 'CREATE_COMMENTS')")
   public CommentCreateResponseDto CreateComment(@Valid @PathVariable AccName accountName,
                                                 @Valid @PathVariable NotePath pathNote,
                                                 @Valid @RequestBody CommentCreateRequestDto createDto) {
//      Check(accountName);
//      CommentCreateResponseDto note = await commentEditService.CreateComment(accountName, pathNote, createDto);
//
//      return Created(Url.Action("GetNote", "PublicNote",
//              new { accountName.AccountName, pathNote = note.Path }, Request.Scheme), note);
   }

   @PostMapping("/comments/{pathNote}")
   @PreAuthorize("hasAnyAuthority('READ_COMMENTS', 'READ_NOTES', 'EDIT_OWN_COMMENTS')")
   public CommentEditResponseDto EditComment(@Valid @PathVariable AccName accountName,
                                             @Valid @PathVariable NotePath pathNote,
                                             @Valid @RequestBody CommentEditRequestDto createDto) {
//      Check(accountName);
//
//      return await commentEditService.EditContentComment(pathNote, accountName, createDto);
   }

   @DeleteMapping("/comments/{pathNote}")
   @PreAuthorize("hasAnyAuthority('READ_COMMENTS', 'READ_NOTES', 'DELETE_COMMENTS')")
   public ActionResult DelComment(@Valid @PathVariable AccName accountName,
                                  @Valid @PathVariable NotePath pathNote) {
//      Check(accountName);
//
//      await commentEditService.Delete(pathNote, accountName);
//      return NoContent();
   }
}
