package com.notes.services.managers;

import com.notes.exceptions.CommentEditTimeMissedException;
import com.notes.exceptions.NoteNotFoundException;
import com.notes.exceptions.NoteTypeException;
import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.models.domain.CommentEditDto;
import com.notes.models.domain.NoteContentDomainDto;
import com.notes.models.domain.NoteCreateDto;
import com.notes.models.domain.NoteTypeDomainDto;
import com.notes.models.entity.Account;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteContent;
import com.notes.models.entity.NoteDto;
import com.notes.repository.NoteRepository;
import com.notes.services.utils.GeneratorUtils;
import com.notes.services.utils.NoteUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentEditService {
   private final NoteInformationService noteInformationService;
   private final NoteUtils noteUtils;
   private final NoteRepository noteRepository;
   private final NoteRepositoryMapper noteRepositoryMapper;
   private final GeneratorUtils generatorUtils;
   private final AccountInformationService accountInformationService;

   public NoteContentDomainDto editComment(String pathComment, String ownerName,
                                           CommentEditDto dto) {
      var commentEntity = getComment(pathComment, ownerName);
      commentEntity.content().setSyntaxType(noteRepositoryMapper.of(dto.syntaxType()));
      commentEntity.content().setContent(dto.content());
      commentEntity.note().setNoteType(noteRepositoryMapper.of(NoteTypeDomainDto.COMMENT_REDACTED));
      commentEntity.note().setTitle(dto.title());
      var r = noteRepository.save(commentEntity);
      return noteRepositoryMapper.of(r);
   }

   public NoteContentDomainDto createComment(String accountName, String pathNote,
                                             NoteCreateDto dto) {
      var account = accountInformationService.findAccount(accountName);
      var note = noteInformationService.findPublicByPath(pathNote, accountName);
      var noteNew = Note.builder()
              .path(generatorUtils.generateUUID().toString())
              .mainNote(Note.builder().id(note.id()).build())
              .description(dto.description())
              .title(dto.title())
              .owner(Account.builder().id(account.id()).build())
              .noteType(noteRepositoryMapper.of(NoteTypeDomainDto.COMMENT))
              .syntaxType(noteRepositoryMapper.of(dto.syntaxType()))
              .isPublic(true)
              .build();
      var comment = new NoteDto(noteNew,
              NoteContent.builder()
                      .content(dto.content())
                      .title(dto.title())
                      .title(noteNew.getTitle())
                      .uuid(noteNew.getElasticUuid())
                      .owner(account.id())
                      .noteType(noteNew.getNoteType())
                      .syntaxType(noteNew.getSyntaxType())
                      .build()
      );
      var r = noteRepository.save(comment);
      return noteRepositoryMapper.of(r);
   }

   public void deleteComment(String pathComment, String ownerName) {
      var commentEntity = getComment(pathComment, ownerName);
      noteRepository.delete(commentEntity.note());
   }

   private NoteDto getComment(String pathComment, String ownerName) {
      var comment = noteInformationService.findPublicByPath(pathComment, ownerName);
      var commentEntity = noteRepository.findById(comment.id()).orElseThrow(NoteNotFoundException::new);
      if (!noteUtils.isComment(comment.noteType())) throw new NoteTypeException();
      if (!noteUtils.isEdit(comment)) throw new CommentEditTimeMissedException();
      return commentEntity;
   }
}
