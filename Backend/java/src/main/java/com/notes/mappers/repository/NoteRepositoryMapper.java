package com.notes.mappers.repository;

import com.notes.configs.MapstructConfig;
import com.notes.models.domain.NoteFullDomainDto;
import com.notes.models.domain.NoteMinimalDomainDto;
import com.notes.models.domain.NotePartialDomainDto;
import com.notes.models.domain.NoteSearchDomainDto;
import com.notes.models.domain.NoteSearchTagsDomainDto;
import com.notes.models.domain.NoteTagsDomainDto;
import com.notes.models.domain.NoteTypeDomainDto;
import com.notes.models.domain.SyntaxTypeDomainDto;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteContent;
import com.notes.models.entity.NoteScored;
import com.notes.models.entity.NoteType;
import com.notes.models.entity.SyntaxType;
import com.notes.models.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(config = MapstructConfig.class)
public interface NoteRepositoryMapper {
   NoteType of(NoteTypeDomainDto api);

   NoteTypeDomainDto of(NoteType api);

   SyntaxType of(SyntaxTypeDomainDto api);

   SyntaxTypeDomainDto of(SyntaxType api);

   //   @Mapping(source = "note.mainNote.path", target = "mainPath")
   NoteMinimalDomainDto of(Note note);

   @Mapping(source = "note.id", target = "id")
   @Mapping(source = "note.noteType", target = "noteType")
   @Mapping(source = "note.isPublic", target = "isPublic")
   @Mapping(source = "note.createdOn", target = "createdOn")
   @Mapping(source = "note.path", target = "path")
   @Mapping(source = "note.owner", target = "owner")
   @Mapping(source = "note.mainNote.path", target = "mainPath")
   @Mapping(source = "note.state", target = "state")
   @Mapping(source = "note.actual.syntaxType", target = "syntaxType")
   @Mapping(source = "note.actual.description", target = "description")
   @Mapping(source = "note.actual.title", target = "title")
   @Mapping(source = "score", target = "score")
   NoteSearchDomainDto of(NoteScored content);

   @Mapping(source = "dto.note.id", target = "id")
   @Mapping(source = "dto.note.noteType", target = "noteType")
   @Mapping(source = "dto.note.isPublic", target = "isPublic")
   @Mapping(source = "dto.note.createdOn", target = "createdOn")
   @Mapping(source = "dto.note.path", target = "path")
   @Mapping(source = "dto.note.owner", target = "owner")
   @Mapping(source = "dto.note.mainNote.path", target = "mainPath")
   @Mapping(source = "dto.note.state", target = "state")
   @Mapping(source = "dto.note.actual.syntaxType", target = "syntaxType")
   @Mapping(source = "dto.note.actual.description", target = "description")
   @Mapping(source = "dto.note.actual.title", target = "title")
   @Mapping(source = "dto.score", target = "score")
   @Mapping(source = "tags", target = "tags")
   NoteSearchTagsDomainDto of(NoteScored dto, List<Tag> tags);


   @Mapping(source = "actual.syntaxType", target = "syntaxType")
   @Mapping(source = "actual.description", target = "description")
   @Mapping(source = "actual.title", target = "title")
   @Mapping(source = "actual.content", target = "content")
   @Mapping(source = "mainNote.path", target = "mainPath")
   @Mapping(target = "withContent", ignore = true)
   @Mapping(target = "withSyntaxType", ignore = true)
   NotePartialDomainDto ofPartial(Note note);

   NoteFullDomainDto of(NotePartialDomainDto note, List<Tag> tags);

   NoteSearchTagsDomainDto of(NoteSearchDomainDto note, List<Tag> tags);

   @Mapping(source = "content.score", target = "score")
   @Mapping(source = "note", target = "note")
   NoteScored of(Note note, NoteContent content);

   @Mapping(source = "score", target = "score")
   @Mapping(source = "note", target = "note")
   NoteScored of(Note note, BigDecimal score);
}
