package com.notes.mappers.repository;

import com.notes.configs.MapstructConfig;
import com.notes.models.domain.EntryFullDomainDto;
import com.notes.models.domain.EntryMinimalDomainDto;
import com.notes.models.domain.EntryPartialDomainDto;
import com.notes.models.domain.EntryTypeDomainDto;
import com.notes.models.domain.NoteSearchDomainDto;
import com.notes.models.domain.NoteSearchTagsDomainDto;
import com.notes.models.domain.SyntaxTypeDomainDto;
import com.notes.models.entity.Entry;
import com.notes.models.entity.EntryType;
import com.notes.models.entity.EntryScored;
import com.notes.models.entity.SyntaxType;
import com.notes.models.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(config = MapstructConfig.class)
public interface EntryRepositoryMapper {
   EntryType of(EntryTypeDomainDto api);

   EntryTypeDomainDto of(EntryType api);

   SyntaxType of(SyntaxTypeDomainDto api);

   SyntaxTypeDomainDto of(SyntaxType api);

   //   @Mapping(source = "note.mainNote.path", target = "mainPath")
   EntryMinimalDomainDto of(Entry entry);

   @Mapping(source = "entry.id", target = "id")
   @Mapping(source = "entry.entryType", target = "entryType")
   @Mapping(source = "entry.isPublic", target = "isPublic")
   @Mapping(source = "entry.createdOn", target = "createdOn")
   @Mapping(source = "entry.path", target = "path")
   @Mapping(source = "entry.owner", target = "owner")
   @Mapping(source = "entry.mainEntry.path", target = "mainPath")
   @Mapping(source = "entry.state", target = "state")
   @Mapping(source = "entry.actual.syntaxType", target = "syntaxType")
   @Mapping(source = "entry.actual.description", target = "description")
   @Mapping(source = "entry.actual.title", target = "title")
   @Mapping(source = "score", target = "score")
   NoteSearchDomainDto of(EntryScored content);

   @Mapping(source = "dto.entry.id", target = "id")
   @Mapping(source = "dto.entry.entryType", target = "entryType")
   @Mapping(source = "dto.entry.isPublic", target = "isPublic")
   @Mapping(source = "dto.entry.createdOn", target = "createdOn")
   @Mapping(source = "dto.entry.path", target = "path")
   @Mapping(source = "dto.entry.owner", target = "owner")
   @Mapping(source = "dto.entry.mainEntry.path", target = "mainPath")
   @Mapping(source = "dto.entry.state", target = "state")
   @Mapping(source = "dto.entry.actual.syntaxType", target = "syntaxType")
   @Mapping(source = "dto.entry.actual.description", target = "description")
   @Mapping(source = "dto.entry.actual.title", target = "title")
   @Mapping(source = "dto.score", target = "score")
   @Mapping(source = "tags", target = "tags")
   NoteSearchTagsDomainDto of(EntryScored dto, List<Tag> tags);


   @Mapping(source = "actual.syntaxType", target = "syntaxType")
   @Mapping(source = "actual.description", target = "description")
   @Mapping(source = "actual.title", target = "title")
   @Mapping(source = "actual.content", target = "content")
   @Mapping(source = "mainEntry.path", target = "mainPath")
   @Mapping(target = "withContent", ignore = true)
   @Mapping(target = "withSyntaxType", ignore = true)
   EntryPartialDomainDto ofPartial(Entry entry);

   EntryFullDomainDto of(EntryPartialDomainDto note, List<Tag> tags);

   NoteSearchTagsDomainDto of(NoteSearchDomainDto note, List<Tag> tags);

   @Mapping(source = "score", target = "score")
   @Mapping(source = "entry", target = "entry")
   EntryScored of(Entry entry, BigDecimal score);
}
