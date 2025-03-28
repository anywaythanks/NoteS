package com.notes.controllers;

import com.notes.mappers.request.NoteRequestMapper;
import com.notes.mappers.response.NoteResponseMapper;
import com.notes.mappers.response.PageResponseMapper;
import com.notes.models.api.account.AccName;
import com.notes.models.api.note.CommentCreateRequestDto;
import com.notes.models.api.note.CommentCreateResponseDto;
import com.notes.models.api.note.CommentEditRequestDto;
import com.notes.models.api.note.CommentEditResponseDto;
import com.notes.models.api.note.CommentSearchContentResponseDto;
import com.notes.models.api.note.NotePath;
import com.notes.models.api.page.PageDto;
import com.notes.models.api.page.PageLimitDto;
import com.notes.models.api.page.PageSizeDto;
import com.notes.services.managers.CommentEditService;
import com.notes.services.managers.CommentInformationService;
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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/public/{accountName}")
@RequiredArgsConstructor
public class PublicCommentsController {
   private final CommentInformationService commentInformationService;
   private final NoteResponseMapper noteResponseMapper;
   private final PageResponseMapper pageResponseMapper;
   private final CommentEditService commentEditService;
   private final NoteRequestMapper noteRequestMapper;

   @GetMapping(path = "/notes/{pathNote}/comments", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_COMMENTS')")
   public PageDto<CommentSearchContentResponseDto> comments(
           @Valid @PathVariable AccName accountName,
           @Valid @PathVariable NotePath pathNote,
           @Valid @RequestAttribute PageSizeDto page,
           @Valid @RequestAttribute PageLimitDto limit) {
      var comments = commentInformationService
              .comments(accountName.name(), pathNote.path(), page.page(), limit.limit())
              .map(noteResponseMapper::ofCommentContent);
      return pageResponseMapper.of(comments);
   }

   @PostMapping(path = "/notes/{pathNote}/comments", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_COMMENTS', 'READ_NOTES', 'CREATE_COMMENTS')")
   public ResponseEntity<CommentCreateResponseDto> createComment(@Valid @PathVariable AccName accountName,
                                                                 @Valid @PathVariable NotePath pathNote,
                                                                 @Valid @RequestBody CommentCreateRequestDto createDto) {
      var note = commentEditService.createComment(accountName.name(),
              pathNote.path(),
              noteRequestMapper.of(createDto));

      return ResponseEntity.created(ServletUriComponentsBuilder
                      .fromPath("/api/public/{accountName}/notes/{pathNote}")
                      .buildAndExpand(accountName.name(), note.path()).toUri())
              .body(noteResponseMapper.ofCommentCreate(note));
   }

   @PostMapping("/comments/{pathNote}")
   @PreAuthorize("hasAnyAuthority('READ_COMMENTS', 'READ_NOTES', 'EDIT_OWN_COMMENTS')")
   public CommentEditResponseDto editComment(@Valid @PathVariable AccName accountName,
                                             @Valid @PathVariable NotePath pathNote,
                                             @Valid @RequestBody CommentEditRequestDto createDto) {
      var comment = commentEditService.editComment(pathNote.path(),
              accountName.name(),
              noteRequestMapper.of(createDto));

      return noteResponseMapper.ofCommentEdit(comment);
   }

   @DeleteMapping("/comments/{pathNote}")
   @PreAuthorize("hasAnyAuthority('READ_COMMENTS', 'READ_NOTES', 'DELETE_COMMENTS')")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delComment(@Valid @PathVariable AccName accountName,
                          @Valid @PathVariable NotePath pathNote) {
      commentEditService.deleteComment(pathNote.path(), accountName.name());
   }
}
