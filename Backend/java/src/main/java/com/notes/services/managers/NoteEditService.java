package com.notes.services.managers;

import com.notes.exceptions.NoteForbiddenException;
import com.notes.exceptions.NoteNotFoundException;
import com.notes.exceptions.NoteTypeException;
import com.notes.mappers.repository.EntryRepositoryMapper;
import com.notes.models.domain.EntryEditDto;
import com.notes.models.domain.EntryMinimalDomainDto;
import com.notes.models.domain.EntryTypeDomainDto;
import com.notes.models.domain.EntryCreateDomainDto;
import com.notes.models.domain.EntryPublicDto;
import com.notes.models.entity.EntryCreateDto;
import com.notes.repository.AccountRepository;
import com.notes.repository.EntryRepositoryCommand;
import com.notes.repository.EntryRepositoryQuery;
import com.notes.services.utils.GeneratorUtils;
import com.notes.services.utils.EntryUtils;
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
   private final EntryUtils entryUtils;
   private final EntryRepositoryCommand entryRepositoryCommand;
   private final EntryRepositoryMapper entryRepositoryMapper;
   private final GeneratorUtils generatorUtils;
   private final AccountInformationService accountInformationService;
   private final EntryRepositoryQuery entryRepositoryQuery;
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
   public EntryMinimalDomainDto publishNote(String pathComment, String ownerName, EntryPublicDto dto) {
      var note = entryRepositoryQuery.findByPath(pathComment).orElseThrow(NoteNotFoundException::new);
      if(entryUtils.isComment(entryRepositoryMapper.of(note.getEntryType()))) {
         if(!Objects.equals(note.getMainEntry().getOwner().getName(), ownerName))
            throw new NoteForbiddenException();
      } else if(!note.getOwner().getName().equals(ownerName)) throw new NoteForbiddenException();
      entryRepositoryCommand.publish(note.getId(), dto.isPublic());
      return entryRepositoryMapper.of(note);//TODO: мейби ошибка
   }

   public void deleteNote(String pathComment, String ownerName) {
      var note = noteInformationService.findByPath(pathComment, ownerName);
      entryRepositoryCommand.delete(note.id());
   }

   public EntryMinimalDomainDto unsafePublishNote(String pathComment, EntryPublicDto dto) {
      var note = entryRepositoryQuery.findByPath(pathComment).orElseThrow(NoteNotFoundException::new);
      entryRepositoryCommand.publish(note.getId(), dto.isPublic());
      return entryRepositoryMapper.of(note);
   }

   public EntryMinimalDomainDto editNote(String pathComment, String ownerName, EntryEditDto dto) {
      var note = noteInformationService.findByPath(pathComment, ownerName);
      return editNote(note, dto);
   }

   public EntryMinimalDomainDto unsafeEditNote(String pathComment, EntryEditDto dto) {
      var note = noteInformationService.unsafeFindByPath(pathComment);
      return editNote(note, dto);
   }

   private EntryMinimalDomainDto editNote(EntryMinimalDomainDto noteDto,
                                          EntryEditDto dto) {
      if(noteDto.entryType() != EntryTypeDomainDto.NOTE) throw new NoteTypeException();
      var note = entryRepositoryQuery.findById(noteDto.id()).orElseThrow(NoteNotFoundException::new);
      entryRepositoryCommand.edit(note.getId(), new com.notes.models.entity.EntryEditDto(
              dto.description(),
              dto.title(),
              dto.content(),
              entryRepositoryMapper.of(dto.syntaxType()),
              note.getEntryType()
      ));
      return entryRepositoryMapper.of(note);
   }

   public EntryMinimalDomainDto createNote(String accountName,
                                           EntryCreateDomainDto dto) {
      var account = accountInformationService.findAccount(accountName);
      var createDto = new EntryCreateDto(
              dto.description(),
              dto.title(),
              generatorUtils.generateUUID().toString(),
              dto.content(),
              generatorUtils.generateUUID(),
              account.id(),
              null,
              entryRepositoryMapper.of(dto.syntaxType()),
              entryRepositoryMapper.of(EntryTypeDomainDto.NOTE),
              false
      );
      entryRepositoryCommand.create(createDto);
      return noteInformationService.findByPath(createDto.path(), accountName);//TODO: зачем..? x2
   }
}
