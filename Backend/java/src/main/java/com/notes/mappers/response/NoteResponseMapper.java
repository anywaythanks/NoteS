package com.notes.mappers.response;

import com.notes.configs.MapstructConfig;
import com.notes.models.api.note.CommentSearchContentResponseDto;
import com.notes.models.api.note.EntrySearchTagsResponseDto;
import com.notes.models.api.note.EntryTypeApiDto;
import com.notes.models.api.note.EntryFullResponseDto;
import com.notes.models.api.note.SyntaxTypeApiDto;
import com.notes.models.domain.EntryPartialDomainDto;
import com.notes.models.domain.EntryFullDomainDto;
import com.notes.models.domain.NoteSearchTagsDomainDto;
import com.notes.models.domain.EntryTypeDomainDto;
import com.notes.models.domain.SyntaxTypeDomainDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface NoteResponseMapper {
   EntryTypeApiDto of(EntryTypeDomainDto api);

   SyntaxTypeApiDto of(SyntaxTypeDomainDto api);

   @Mapping(source = "dto.owner.name", target = "ownerName")
   CommentSearchContentResponseDto ofCommentContent(EntryPartialDomainDto dto);

   @Mapping(source = "dto.owner.name", target = "ownerName")
   EntryFullResponseDto ofFull(EntryFullDomainDto dto);

   @Mapping(source = "dto.owner.name", target = "ownerName")
   EntrySearchTagsResponseDto ofTags(NoteSearchTagsDomainDto dto);
}
