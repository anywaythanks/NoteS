package com.notes.services.managers;

import com.notes.exceptions.NoteForbiddenException;
import com.notes.exceptions.NoteNotFoundException;
import com.notes.exceptions.NoteTypeException;
import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.models.domain.NoteCreateDomainDto;
import com.notes.models.domain.NoteEditOnlyContentDto;
import com.notes.models.domain.NoteEditOtherDto;
import com.notes.models.domain.NoteMinimalDomainDto;
import com.notes.models.domain.NotePartialDomainDto;
import com.notes.models.domain.NotePublicDto;
import com.notes.models.domain.NoteTypeDomainDto;
import com.notes.models.entity.NoteCreateDto;
import com.notes.models.entity.NoteEditDto;
import com.notes.repository.AccountRepository;
import com.notes.repository.NoteRepositoryCommand;
import com.notes.repository.NoteRepositoryQuery;
import com.notes.services.utils.GeneratorUtils;
import com.notes.services.utils.NoteUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service handling note modification operations including creation, editing, publishing, and deletion.
 * Manages both core note content and metadata updates with ownership validation.
 */
@Service
@RequiredArgsConstructor
public class NoteEditService {
   private final NoteInformationService noteInformationService;
   private final NoteUtils noteUtils;
   private final NoteRepositoryCommand noteRepositoryCommand;
   private final NoteRepositoryMapper noteRepositoryMapper;
   private final GeneratorUtils generatorUtils;
   private final AccountInformationService accountInformationService;
   private final NoteRepositoryQuery noteRepositoryQuery;
   private final AccountRepository accountRepository;

   /**
    * Publishes or unpublishes a note with ownership validation.
    *
    * @param pathComment Note's path identifier
    * @param ownerName   Account name of the requester
    * @param dto         Publication status DTO
    * @return Updated NotePartialDomainDto
    * @throws NoteNotFoundException  If note not found
    * @throws NoteForbiddenException If requester isn't note owner/comment parent owner
    * @throws NoteTypeException      If invalid note type for operation
    */
   public NoteMinimalDomainDto publishNote(String pathComment, String ownerName, NotePublicDto dto) {
      var note = noteRepositoryQuery.findByPath(pathComment).orElseThrow(NoteNotFoundException::new);
      if(noteUtils.isComment(noteRepositoryMapper.of(note.getNoteType()))) {
         if(!Objects.equals(note.getMainNote().getOwner().getName(), ownerName))
            throw new NoteForbiddenException();
      } else if(!note.getOwner().getName().equals(ownerName)) throw new NoteForbiddenException();
      noteRepositoryCommand.publish(note.getId(), dto.isPublic());
      return noteRepositoryMapper.of(note);//TODO: мейби ошибка
   }

   public void deleteNote(String pathComment, String ownerName) {
      var note = noteInformationService.findByPath(pathComment, ownerName);
      noteRepositoryCommand.delete(note.id());
   }

   public NoteMinimalDomainDto unsafePublishNote(String pathComment, NotePublicDto dto) {
      var note = noteRepositoryQuery.findByPath(pathComment).orElseThrow(NoteNotFoundException::new);
      noteRepositoryCommand.publish(note.getId(), dto.isPublic());
      return noteRepositoryMapper.of(note);
   }

   public NoteMinimalDomainDto editNote(String pathComment, String ownerName, NoteEditOtherDto dto) {
      var note = noteInformationService.findByPath(pathComment, ownerName);
      return editNote(note, dto);
   }

   public NoteMinimalDomainDto unsafeEditNote(String pathComment, NoteEditOtherDto dto) {
      var note = noteInformationService.unsafeFindByPath(pathComment);
      return editNote(note, dto);
   }

   private NoteMinimalDomainDto editNote(NoteMinimalDomainDto noteDto,
                                         NoteEditOtherDto dto) {
      if(noteDto.noteType() != NoteTypeDomainDto.NOTE) throw new NoteTypeException();
      var note = noteRepositoryQuery.findById(noteDto.id()).orElseThrow(NoteNotFoundException::new);
      noteRepositoryCommand.edit(note.getId(), new NoteEditDto(
              dto.description(),
              dto.title(),
              note.getActual().getContent(),
              note.getActual().getSyntaxType(),
              note.getNoteType()
      ));
      return noteRepositoryMapper.of(note);
   }

   public NotePartialDomainDto editContentNote(String pathComment, String ownerName, NoteEditOnlyContentDto dto) {
      var note = noteInformationService.findByPath(pathComment, ownerName);
      return editContentNote(note, dto);
   }

   public NotePartialDomainDto unsafeEditContentNote(String pathComment, NoteEditOnlyContentDto dto) {
      var note = noteInformationService.unsafeFindByPath(pathComment);
      return editContentNote(note, dto);
   }

   private NotePartialDomainDto editContentNote(NoteMinimalDomainDto noteDto,
                                                NoteEditOnlyContentDto dto) {
      var note = noteRepositoryQuery.findById(noteDto.id()).orElseThrow(NoteNotFoundException::new);
      if(noteDto.noteType() != NoteTypeDomainDto.NOTE) throw new NoteTypeException();
      noteRepositoryCommand.edit(note.getId(), new NoteEditDto(
              note.getActual().getDescription(),
              note.getActual().getTitle(),
              dto.content(),
              noteRepositoryMapper.of(dto.syntaxType()),
              note.getNoteType()
      ));
      return noteRepositoryMapper.ofPartial(note);

   }

   public NoteMinimalDomainDto createNote(String accountName,
                                          NoteCreateDomainDto dto) {
      var account = accountInformationService.findAccount(accountName);
      var createDto = new NoteCreateDto(
              dto.description(),
              dto.title(),
              generatorUtils.generateUUID().toString(),
              dto.content(),
              generatorUtils.generateUUID(),
              account.id(),
              null,
              noteRepositoryMapper.of(dto.syntaxType()),
              noteRepositoryMapper.of(NoteTypeDomainDto.NOTE),
              false
      );
      noteRepositoryCommand.create(createDto);
      return noteInformationService.findByPath(createDto.path(), accountName);//TODO: зачем..? x2
   }
}
