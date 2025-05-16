package com.notes.services.managers;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain2.NamedEntityGraph;
import com.notes.exceptions.CommentEditTimeMissedException;
import com.notes.exceptions.NoteNotFoundException;
import com.notes.exceptions.NoteTypeException;
import com.notes.mappers.repository.EntryRepositoryMapper;
import com.notes.models.domain.CommentEditDto;
import com.notes.models.domain.EntryPartialDomainDto;
import com.notes.models.domain.EntryTypeDomainDto;
import com.notes.models.domain.EntryCreateDomainDto;
import com.notes.models.domain.NoteSearchDomainDto;
import com.notes.models.entity.Entry;
import com.notes.models.entity.EntryCreateDto;
import com.notes.models.entity.EntryEditDto;
import com.notes.models.entity.EntryScored;
import com.notes.models.entity.Entry_;
import com.notes.repository.EntryRepositoryCommand;
import com.notes.repository.EntryRepositoryQuery;
import com.notes.services.utils.GeneratorUtils;
import com.notes.services.utils.EntryUtils;
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
   private final EntryUtils entryUtils;
   private final EntryRepositoryCommand entryRepositoryCommand;
   private final EntryRepositoryMapper entryRepositoryMapper;
   private final GeneratorUtils generatorUtils;
   private final AccountInformationService accountInformationService;
   private final EntryRepositoryQuery entryRepositoryQuery;

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
   public EntryPartialDomainDto editComment(String pathComment, String ownerName,
                                            CommentEditDto dto) {
      var comment = getComment(pathComment, ownerName);
      entryRepositoryCommand.edit(comment.getId(), new EntryEditDto(
              comment.getActual().getDescription(),
              dto.title(),
              dto.content(),
              entryRepositoryMapper.of(dto.syntaxType()),
              entryRepositoryMapper.of(EntryTypeDomainDto.COMMENT)
      ));
      return entryRepositoryMapper.ofPartial(comment);
   }

   /**
    * Creates a new comment associated with a parent note.
    *
    * @param accountName The name of the commenting account
    * @param pathNote    The path of the parent note
    * @param dto         Data transfer object containing comment details
    * @return The created {@link NoteSearchDomainDto}
    */
   public EntryPartialDomainDto createComment(String accountName, String pathNote,
                                              EntryCreateDomainDto dto) {
      var account = accountInformationService.findAccount(accountName);
      var note = noteInformationService.findPublicByPath(pathNote, accountName);
      var createDto = new EntryCreateDto(
              dto.description(),
              dto.title(),
              generatorUtils.generateUUID().toString(),
              dto.content(),
              generatorUtils.generateUUID(),
              account.id(),
              note.id(),
              entryRepositoryMapper.of(dto.syntaxType()),
              entryRepositoryMapper.of(EntryTypeDomainDto.COMMENT),
              true
      );
      entryRepositoryCommand.create(createDto);
      return entryRepositoryMapper.ofPartial(getComment(createDto.path(), accountName));//TODO: зачем..?
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
      entryRepositoryCommand.delete(comment.getId());
   }

   /**
    * Retrieves and validates a comment entity.
    *
    * @param pathComment The path identifier of the comment
    * @param ownerName   The name of the comment owner
    * @return Validated {@link EntryScored} comment entity
    * @throws NoteNotFoundException          If comment not found
    * @throws NoteTypeException              If the note is not a comment
    * @throws CommentEditTimeMissedException If edit window has expired
    */
   private Entry getComment(String pathComment, String ownerName) {
      var comment = noteInformationService.findPublicByPath(pathComment, ownerName);
      var commentEntity = entryRepositoryQuery.findById(comment.id(),
              new NamedEntityGraph(EntityGraphType.LOAD, Entry_.GRAPH_ENTRY_ACTUAL_FULL)).orElseThrow(NoteNotFoundException::new);
      if(!entryUtils.isComment(comment.entryType())) throw new NoteTypeException();
      if(!entryUtils.isEdit(comment)) throw new CommentEditTimeMissedException();
      return commentEntity;
   }
}
