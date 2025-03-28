package com.notes.mappers.repository;

import com.notes.configs.MapstructConfig;
import com.notes.models.domain.NoteContentDomainDto;
import com.notes.models.domain.NoteFullDomainDto;
import com.notes.models.domain.NotePartialDomainDto;
import com.notes.models.domain.NoteTagsDomainDto;
import com.notes.models.domain.NoteTypeDomainDto;
import com.notes.models.domain.SyntaxTypeDomainDto;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteContent;
import com.notes.models.entity.NoteDto;
import com.notes.models.entity.NoteType;
import com.notes.models.entity.SyntaxType;
import com.notes.models.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfig.class)
public interface NoteRepositoryMapper {
   NoteType of(NoteTypeDomainDto api);

   NoteTypeDomainDto of(NoteType api);

   SyntaxType of(SyntaxTypeDomainDto api);

   SyntaxTypeDomainDto of(SyntaxType api);

   NotePartialDomainDto of(Note note);

   @Mapping(source = "note.id", target = "id")
   @Mapping(source = "note.description", target = "description")
   @Mapping(source = "note.title", target = "title")
   @Mapping(source = "note.path", target = "path")
   @Mapping(source = "note.owner", target = "owner")
   @Mapping(source = "note.elasticUuid", target = "elasticUuid")
   @Mapping(source = "note.mainNote.path", target = "mainPath")
   @Mapping(source = "note.syntaxType", target = "syntaxType")
   @Mapping(source = "note.noteType", target = "noteType")
   @Mapping(source = "note.isPublic", target = "isPublic")
   @Mapping(source = "note.createdOn", target = "createdOn")
   @Mapping(source = "content.score", target = "score")
   @Mapping(source = "content.content", target = "content")
   NoteContentDomainDto of(NoteDto content);
   @Mapping(source = "dto.note.id", target = "id")
   @Mapping(source = "dto.note.description", target = "description")
   @Mapping(source = "dto.note.title", target = "title")
   @Mapping(source = "dto.note.path", target = "path")
   @Mapping(source = "dto.note.elasticUuid", target = "elasticUuid")
   @Mapping(source = "dto.note.owner", target = "owner")
   @Mapping(source = "dto.note.mainNote.path", target = "mainPath")
   @Mapping(source = "dto.note.syntaxType", target = "syntaxType")
   @Mapping(source = "dto.note.noteType", target = "noteType")
   @Mapping(source = "dto.note.isPublic", target = "isPublic")
   @Mapping(source = "dto.note.createdOn", target = "createdOn")
   @Mapping(source = "dto.content.score", target = "score")
   @Mapping(source = "dto.content.content", target = "content")
   NoteFullDomainDto of(NoteDto dto, List<Tag> tags);

   @Mapping(source = "note.id", target = "id")
   @Mapping(source = "note.description", target = "description")
   @Mapping(source = "note.title", target = "title")
   @Mapping(source = "note.path", target = "path")
   @Mapping(source = "note.owner", target = "owner")
   @Mapping(source = "note.mainPath", target = "mainPath")
   @Mapping(source = "note.elasticUuid", target = "elasticUuid")
   @Mapping(source = "note.syntaxType", target = "syntaxType")
   @Mapping(source = "note.noteType", target = "noteType")
   @Mapping(source = "note.isPublic", target = "isPublic")
   @Mapping(source = "note.createdOn", target = "createdOn")
   @Mapping(source = "content.score", target = "score")
   @Mapping(source = "content.content", target = "content")
   NoteContentDomainDto of(NotePartialDomainDto note, NoteContent content);

   NoteFullDomainDto of(NoteContentDomainDto content, List<Tag> tags);

   NoteTagsDomainDto of(NotePartialDomainDto content, List<Tag> tags);

   NoteDto of(Note note, NoteContent noteContent);
}
