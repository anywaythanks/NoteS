package com.notes.controllers;

import com.notes.mappers.request.NoteRequestMapper;
import com.notes.mappers.response.NoteResponseMapper;
import com.notes.mappers.response.PageResponseMapper;
import com.notes.models.api.account.AccName;
import com.notes.models.api.note.CommentCreateRequestDto;
import com.notes.models.api.note.CommentCreateResponseDto;
import com.notes.models.api.note.CommentEditRequestDto;
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
import org.springframework.web.bind.annotation.RequestParam;
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

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicComments/get_api_public__accountName__notes__pathNote__comments">Click</a>
    */
   @GetMapping(path = "/notes/{pathNote}/comments")
   @PreAuthorize("hasAnyAuthority('read-comments')")
   public PageDto<CommentSearchContentResponseDto> comments(
           @Valid @PathVariable("accountName") AccName accountName,
           @Valid @PathVariable NotePath pathNote,
           @Valid @RequestParam("Page") PageSizeDto page,
           @Valid @RequestParam("Limit") PageLimitDto limit) {
      var comments = commentInformationService
              .comments(accountName.name(), pathNote.path(), page.page(), limit.limit())
              .map(noteResponseMapper::ofCommentContent);
      return pageResponseMapper.of(comments);
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicComments/post_api_public__accountName__notes__pathNote__comments">Click</a>
    */
   @PostMapping(path = "/notes/{pathNote}/comments", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-comments', 'read-notes', 'create-comments')")
   public ResponseEntity<CommentCreateResponseDto> createComment(@Valid @PathVariable("accountName") AccName accountName,
                                                                 @Valid @PathVariable NotePath pathNote,
                                                                 @Valid @RequestBody CommentCreateRequestDto createDto) {
      var note = commentEditService.createComment(accountName.name(),
              pathNote.path(),
              noteRequestMapper.of(createDto));

      return ResponseEntity.created(ServletUriComponentsBuilder
                      .fromPath("{pathNote}")
                      .buildAndExpand(note.path()).toUri())
              .build();
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicComments/post_api_public__accountName__comments__pathNote_">Click</a>
    */
   @PostMapping("/comments/{pathNote}")
   @PreAuthorize("hasAnyAuthority('read-comments', 'read-notes', 'edit-own-comments')")
   public void editComment(@Valid @PathVariable("accountName") AccName accountName,
                           @Valid @PathVariable NotePath pathNote,
                           @Valid @RequestBody CommentEditRequestDto createDto) {
      commentEditService.editComment(pathNote.path(),
              accountName.name(),
              noteRequestMapper.of(createDto));
   }

   /**
    * <a href="https://anywaythanks.github.io/NoteS-API/#/PublicComments/delete_api_public__accountName__comments__pathNote_">Click</a>
    */
   @DeleteMapping("/comments/{pathNote}")
   @PreAuthorize("hasAnyAuthority('read-comments', 'read-notes', 'delete-comments')")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delComment(@Valid @PathVariable("accountName") AccName accountName,
                          @Valid @PathVariable NotePath pathNote) {
      commentEditService.deleteComment(pathNote.path(), accountName.name());
   }
}
