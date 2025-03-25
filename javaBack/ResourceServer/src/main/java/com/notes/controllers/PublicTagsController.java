package com.notes.controllers;

import com.notes.models.dto.account.AccName;
import com.notes.models.dto.note.NotePath;
import com.notes.models.dto.tag.AddTagRequestDto;
import com.notes.models.dto.tag.CreateTagRequestDto;
import com.notes.models.dto.tag.TagNameRequestDto;
import com.notes.models.dto.tag.TagResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/private/{accountName}/notes")
@RequiredArgsConstructor
public class PublicTagsController {
   private final TagInformationService tagInformationService;
   private final AccountRegisterService register;
   private final TagEditService tagEditService;


   @GetMapping(path = "/notes/{pathNote}/tags", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES')")
   public List<TagResponseDto> Tags(@Valid @PathVariable AccName accountName,
                                    @Valid @PathVariable NotePath pathNote) {
//      Check(accountName);
//      var tags = tagInformationService.Tags(pathNote, accountName);
//      return um.Of(tags);
   }

   @PostMapping(path = "/tags", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES')")
   public CreatedResult CreateTag(@Valid @PathVariable AccName accountName,
                                  @Valid @RequestBody CreateTagRequestDto createTagRequestDto) {
//      Check(accountName);
//      TagResponseDto note = tagEditService.Create(accountName, createTagRequestDto);
//      return Created(Url.Action("Tags", "PublicTags",
//              new { accountName.AccountName }, Request.Scheme), note);
   }


   @GetMapping(path = "/tags", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES')")
   public List<TagResponseDto> Tags(@Valid @PathVariable AccName accountName) {
//      Check(accountName);
//
//      return um.Of(tagInformationService.Tags(accountName));
   }

   @DeleteMapping(path = "/notes/{pathNote}/tags/{tagName}", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES')")
   public NoContentResult DelTag(@Valid @PathVariable AccName accountName,
                                 @Valid @PathVariable NotePath pathNote,
                                 @Valid @PathVariable TagNameRequestDto tagName) {
//      Check(accountName);
//
//      tagEditService.Delete(pathNote, accountName, delete);
//      return NoContent();
   }

   @PostMapping(path = "/notes/{pathNote}/tags", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAnyAuthority('READ_NOTES')")
   public ActionResult AddTag(@Valid @PathVariable AccName accountName,
                              @Valid @PathVariable NotePath pathNote,
                              @Valid @RequestBody AddTagRequestDto createTagRequestDto) {
//      Check(accountName);
//      var isCreate = tagEditService.Add(pathNote, accountName, createTagRequestDto);
//      return isCreate ? Created() : Ok();
   }
}
