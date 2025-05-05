package com.notes.services.managers;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain2.NamedEntityGraph;
import com.notes.exceptions.CommentEditTimeMissedException;
import com.notes.exceptions.NoteNotFoundException;
import com.notes.exceptions.NoteTypeException;
import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.models.domain.CommentEditDto;
import com.notes.models.domain.NoteCreateDomainDto;
import com.notes.models.domain.NotePartialDomainDto;
import com.notes.models.domain.NoteSearchDomainDto;
import com.notes.models.domain.NoteTypeDomainDto;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteCreateDto;
import com.notes.models.entity.NoteEditDto;
import com.notes.models.entity.NoteScored;
import com.notes.repository.NoteRepositoryCommand;
import com.notes.repository.NoteRepositoryQuery;
import com.notes.services.utils.GeneratorUtils;
import com.notes.services.utils.NoteUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handling comment editing operations including creation, modification, and deletion.
 * Manages comment lifecycle with validation checks for edit windows and comment types.
 */
@Service
@RequiredArgsConstructor
public class CommentEditService {
   private final NoteInformationService noteInformationService;
   private final NoteUtils noteUtils;
   private final NoteRepositoryCommand noteRepositoryCommand;
   private final NoteRepositoryMapper noteRepositoryMapper;
   private final GeneratorUtils generatorUtils;
   private final AccountInformationService accountInformationService;
   private final NoteRepositoryQuery noteRepositoryQuery;

   /**
    * Edits an existing comment with validation checks.
    *
    * @param pathComment The path identifier of the comment to edit
    * @param ownerName   The name of the comment owner
    * @param dto         Data transfer object containing edit information
    * @return The updated {@link NoteSearchDomainDto}
    * @throws NoteNotFoundException          If comment not found
    * @throws NoteTypeException              If the note is not a comment
    * @throws CommentEditTimeMissedException If edit window has expired
    */
   public NotePartialDomainDto editComment(String pathComment, String ownerName,
                                           CommentEditDto dto) {
      var comment = getComment(pathComment, ownerName);
      noteRepositoryCommand.edit(comment.getId(), new NoteEditDto(
              comment.getActual().getDescription(),
              dto.title(),
              dto.content(),
              noteRepositoryMapper.of(dto.syntaxType()),
              noteRepositoryMapper.of(NoteTypeDomainDto.COMMENT_REDACTED)
      ));
      return noteRepositoryMapper.ofPartial(comment);
   }

   /**
    * Creates a new comment associated with a parent note.
    *
    * @param accountName The name of the commenting account
    * @param pathNote    The path of the parent note
    * @param dto         Data transfer object containing comment details
    * @return The created {@link NoteSearchDomainDto}
    */
   public NotePartialDomainDto createComment(String accountName, String pathNote,
                                             NoteCreateDomainDto dto) {
      var account = accountInformationService.findAccount(accountName);
      var note = noteInformationService.findPublicByPath(pathNote, accountName);
      var createDto = new NoteCreateDto(
              dto.description(),
              dto.title(),
              generatorUtils.generateUUID().toString(),
              dto.content(),
              generatorUtils.generateUUID(),
              account.id(),
              note.id(),
              noteRepositoryMapper.of(dto.syntaxType()),
              noteRepositoryMapper.of(NoteTypeDomainDto.COMMENT),
              true
      );
      noteRepositoryCommand.create(createDto);
      return noteRepositoryMapper.ofPartial(getComment(createDto.path(), accountName));//TODO: зачем..?
   }

   /**
    * Deletes a comment with validation checks.
    *
    * @param pathComment The path identifier of the comment to delete
    * @param ownerName   The name of the comment owner
    * @throws NoteNotFoundException          If comment not found
    * @throws NoteTypeException              If the note is not a comment
    * @throws CommentEditTimeMissedException If deletion window has expired
    */
   public void deleteComment(String pathComment, String ownerName) {
      var comment = getComment(pathComment, ownerName);
      noteRepositoryCommand.delete(comment.getId());
   }

   /**
    * Retrieves and validates a comment entity.
    *
    * @param pathComment The path identifier of the comment
    * @param ownerName   The name of the comment owner
    * @return Validated {@link NoteScored} comment entity
    * @throws NoteNotFoundException          If comment not found
    * @throws NoteTypeException              If the note is not a comment
    * @throws CommentEditTimeMissedException If edit window has expired
    */
   private Note getComment(String pathComment, String ownerName) {
      var comment = noteInformationService.findPublicByPath(pathComment, ownerName);
      var commentEntity = noteRepositoryQuery.findById(comment.id(), new NamedEntityGraph(EntityGraphType.LOAD, "Note.actual.full")).orElseThrow(NoteNotFoundException::new);
      if(!noteUtils.isComment(comment.noteType())) throw new NoteTypeException();
      if(!noteUtils.isEdit(comment)) throw new CommentEditTimeMissedException();
      return commentEntity;
   }
}
