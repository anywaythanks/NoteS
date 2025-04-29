package com.notes.mappers.response;

import com.notes.configs.MapstructConfig;
import com.notes.models.api.note.CommentCreateResponseDto;
import com.notes.models.api.note.CommentEditResponseDto;
import com.notes.models.api.note.CommentSearchContentResponseDto;
import com.notes.models.api.note.NoteCreateResponseDto;
import com.notes.models.api.note.NoteEditContentResponseDto;
import com.notes.models.api.note.NoteEditOtherResponseDto;
import com.notes.models.api.note.NoteEditPublicResponseDto;
import com.notes.models.api.note.NoteSearchContentResponseDto;
import com.notes.models.api.note.NoteSearchTagsResponseDto;
import com.notes.models.api.note.NoteTypeApiDto;
import com.notes.models.api.note.SyntaxTypeApiDto;
import com.notes.models.domain.NoteMinimalDomainDto;
import com.notes.models.domain.NoteSearchDomainDto;
import com.notes.models.domain.NoteSearchTagsDomainDto;
import com.notes.models.domain.NoteTagsDomainDto;
import com.notes.models.domain.NoteTypeDomainDto;
import com.notes.models.domain.SyntaxTypeDomainDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface NoteResponseMapper {
   NoteTypeApiDto of(NoteTypeDomainDto api);

   SyntaxTypeApiDto of(SyntaxTypeDomainDto api);

   NoteEditPublicResponseDto ofPublic(NoteMinimalDomainDto dto);

   NoteEditOtherResponseDto ofOther(NoteMinimalDomainDto dto);

   NoteEditContentResponseDto ofEditContent(NoteSearchDomainDto dto);

   @Mapping(source = "dto.owner.name", target = "ownerName")
   CommentSearchContentResponseDto ofCommentContent(NoteSearchDomainDto dto);

   CommentCreateResponseDto ofCommentCreate(NoteSearchDomainDto dto);

   NoteCreateResponseDto ofNoteCreate(NoteSearchDomainDto dto);

   @Mapping(source = "dto.owner.name", target = "ownerName")
   NoteSearchContentResponseDto ofFull(NoteSearchTagsDomainDto dto);

   NoteSearchTagsResponseDto ofTags(NoteTagsDomainDto dto);

   CommentEditResponseDto ofCommentEdit(NoteSearchDomainDto dto);
}
