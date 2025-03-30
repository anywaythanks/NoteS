package com.notes.controllers;

import com.notes.mappers.request.TagRequestMapper;
import com.notes.mappers.response.TagResponseMapper;
import com.notes.models.api.account.AccName;
import com.notes.models.api.note.NotePath;
import com.notes.models.api.tag.AddTagRequestDto;
import com.notes.models.api.tag.TagCreateRequestDto;
import com.notes.models.api.tag.TagNameRequestDto;
import com.notes.models.api.tag.TagResponseDto;
import com.notes.services.managers.AccountRegisterService;
import com.notes.services.managers.TagEditService;
import com.notes.services.managers.TagInformationService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/public/{accountName}")
@RequiredArgsConstructor
public class PublicTagsController {
   private final TagInformationService tagInformationService;
   private final TagEditService tagEditService;
   private final TagResponseMapper tagResponseMapper;
   private final TagRequestMapper tagRequestMapper;


   @GetMapping(path = "/notes/{pathNote}/tags", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-notes')")
   public List<TagResponseDto> tags(@Valid @PathVariable AccName accountName,
                                    @Valid @PathVariable NotePath pathNote) {
      return tagInformationService.findTags(pathNote.path(), accountName.name())
              .stream()
              .map(tagResponseMapper::of)
              .toList();
   }

   @PostMapping(path = "/tags", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-notes')")
   public ResponseEntity<TagResponseDto> createTag(@Valid @PathVariable AccName accountName,
                                                   @Valid @RequestBody TagCreateRequestDto tagCreate) {
      var tag = tagEditService.create(accountName.name(),
              tagRequestMapper.of(tagCreate));

      return ResponseEntity.created(ServletUriComponentsBuilder
                      .fromPath("/api/public/{accountName}/tags")
                      .buildAndExpand(accountName.name()).toUri())
              .body(tagResponseMapper.of(tag));
   }


   @GetMapping(path = "/tags", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-notes')")
   public List<TagResponseDto> tags(@Valid @PathVariable AccName accountName) {
      return tagInformationService.findTags(accountName.name())
              .stream()
              .map(tagResponseMapper::of)
              .toList();
   }

   @DeleteMapping(path = "/notes/{pathNote}/tags/{tagName}", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-notes')")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delTag(@Valid @PathVariable AccName accountName,
                      @Valid @PathVariable NotePath pathNote,
                      @Valid @PathVariable TagNameRequestDto tagName) {
      tagEditService.delete(pathNote.path(), accountName.name(), tagName.name());
   }

   @PostMapping(path = "/notes/{pathNote}/tags", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('read-notes')")
   @ResponseStatus(HttpStatus.CREATED)
   public void addTag(@Valid @PathVariable AccName accountName,
                      @Valid @PathVariable NotePath pathNote,
                      @Valid @RequestBody AddTagRequestDto createTagRequestDto) {
      tagEditService.add(pathNote.path(), accountName.name(), createTagRequestDto.name());
   }
}
