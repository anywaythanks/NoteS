package com.notes.services.managers;

import com.notes.exceptions.AccountNotFoundException;
import com.notes.exceptions.NoteForbiddenException;
import com.notes.exceptions.NoteNotFoundException;
import com.notes.exceptions.NoteTypeException;
import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.models.domain.NoteContentDomainDto;
import com.notes.models.domain.NoteCreateDto;
import com.notes.models.domain.NoteEditOnlyContentDto;
import com.notes.models.domain.NoteEditOtherDto;
import com.notes.models.domain.NotePartialDomainDto;
import com.notes.models.domain.NotePublicDto;
import com.notes.models.domain.NoteTypeDomainDto;
import com.notes.models.entity.Account;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteContent;
import com.notes.models.entity.NoteDto;
import com.notes.repository.AccountRepository;
import com.notes.repository.NoteRepository;
import com.notes.repository.NoteRepositoryDb;
import com.notes.repository.NoteRepositoryElastic;
import com.notes.services.utils.GeneratorUtils;
import com.notes.services.utils.NoteUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NoteEditService {
   private final NoteInformationService noteInformationService;
   private final NoteUtils noteUtils;
   private final NoteRepository noteRepository;
   private final NoteRepositoryMapper noteRepositoryMapper;
   private final GeneratorUtils generatorUtils;
   private final AccountInformationService accountInformationService;
   private final NoteRepositoryDb noteRepositoryDb;
   private final NoteRepositoryElastic noteRepositoryElastic;
   private final AccountRepository accountRepository;

   public NotePartialDomainDto publishNote(String pathComment, String ownerName, NotePublicDto dto) {
      var note = noteRepositoryDb.findByPath(pathComment).orElseThrow(NoteNotFoundException::new);
      if (noteUtils.isComment(noteRepositoryMapper.of(note.getNoteType()))) {
         if (!Objects.equals(note.getMainNote().getOwner().getName(), ownerName))
            throw new NoteForbiddenException();
      } else if (!note.getOwner().getName().equals(ownerName)) throw new NoteForbiddenException();
      note.setIsPublic(dto.isPublic());
      var r = noteRepositoryDb.save(note);
      return noteRepositoryMapper.of(r);
   }

   public void deleteNote(String pathComment, String ownerName) {
      var note = noteInformationService.findByPath(pathComment, ownerName);
      var noteEntity = noteRepository.findById(note.id()).orElseThrow(NoteNotFoundException::new);
      noteRepository.delete(noteEntity.note());
   }

   public NotePartialDomainDto unsafePublishNote(String pathComment, NotePublicDto dto) {
      var note = noteRepositoryDb.findByPath(pathComment).orElseThrow(NoteNotFoundException::new);
      note.setIsPublic(dto.isPublic());
      var r = noteRepositoryDb.save(note);
      return noteRepositoryMapper.of(r);
   }

   public NotePartialDomainDto editNote(String pathComment, String ownerName, NoteEditOtherDto dto) {
      var note = noteInformationService.findByPath(pathComment, ownerName);
      return editNote(note, dto);
   }

   public NotePartialDomainDto unsafeEditNote(String pathComment, NoteEditOtherDto dto) {
      var note = noteInformationService.unsafeFindByPath(pathComment);
      return editNote(note, dto);
   }

   private NotePartialDomainDto editNote(NotePartialDomainDto note,
                                         NoteEditOtherDto dto) {
      if (note.noteType() != NoteTypeDomainDto.NOTE) throw new NoteTypeException();
      var noteEntity = noteRepositoryDb.findById(note.id()).orElseThrow(NoteNotFoundException::new);
      noteEntity.setTitle(dto.title());
      noteEntity.setDescription(dto.description());
      var r = noteRepositoryDb.save(noteEntity);
      return noteRepositoryMapper.of(noteEntity);
   }

   public NoteContentDomainDto editContentNote(String pathComment, String ownerName, NoteEditOnlyContentDto dto) {
      var note = noteInformationService.findByPath(pathComment, ownerName);
      return editContentNote(note, dto);
   }

   public NoteContentDomainDto unsafeEditContentNote(String pathComment, NoteEditOnlyContentDto dto) {
      var note = noteInformationService.unsafeFindByPath(pathComment);
      return editContentNote(note, dto);
   }

   private NoteContentDomainDto editContentNote(NotePartialDomainDto note,
                                                NoteEditOnlyContentDto dto) {
      if (note.noteType() != NoteTypeDomainDto.NOTE) throw new NoteTypeException();
      var noteContent = noteRepositoryElastic.findById(note.elasticUuid())
              .orElseThrow(NoteNotFoundException::new);
      noteContent.setContent(dto.content());
      noteContent.setSyntaxType(noteRepositoryMapper.of(dto.syntaxType()));
      var r = noteRepositoryElastic.save(noteContent);
      return noteRepositoryMapper.of(note, r);
   }

   public NoteContentDomainDto createNote(String accountName,
                                          NoteCreateDto dto) {
      var account = accountInformationService.findAccount(accountName);
      var accountEntity = accountRepository.findById(account.id()).orElseThrow(AccountNotFoundException::new);
      var noteNew = Note.builder()
              .path(generatorUtils.generateUUID().toString())
              .elasticUuid(generatorUtils.generateUUID().toString())
              .description(dto.description())
              .owner(accountEntity)
              .noteType(noteRepositoryMapper.of(NoteTypeDomainDto.NOTE))
              .syntaxType(noteRepositoryMapper.of(dto.syntaxType()))
              .title(dto.title())
              .isPublic(false)
              .build();
      var note = new NoteDto(noteNew,
              NoteContent.builder()
                      .content(dto.content())
                      .title(noteNew.getTitle())
                      .uuid(noteNew.getElasticUuid())
                      .owner(account.id())
                      .noteType(noteNew.getNoteType())
                      .syntaxType(noteNew.getSyntaxType())
                      .build()
      );
      var r = noteRepository.save(note);
      return noteRepositoryMapper.of(r);
   }
}
