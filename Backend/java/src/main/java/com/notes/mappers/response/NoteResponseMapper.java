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
import com.notes.models.domain.NoteContentDomainDto;
import com.notes.models.domain.NoteFullDomainDto;
import com.notes.models.domain.NotePartialDomainDto;
import com.notes.models.domain.NoteTagsDomainDto;
import com.notes.models.domain.NoteTypeDomainDto;
import com.notes.models.domain.SyntaxTypeDomainDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface NoteResponseMapper {
   NoteTypeApiDto of(NoteTypeDomainDto api);

   SyntaxTypeApiDto of(SyntaxTypeDomainDto api);

   NoteEditPublicResponseDto ofPublic(NotePartialDomainDto dto);

   NoteEditOtherResponseDto ofOther(NotePartialDomainDto dto);

   NoteEditContentResponseDto ofEditContent(NoteContentDomainDto dto);

   @Mapping(source = "dto.owner.name", target = "ownerName")
   CommentSearchContentResponseDto ofCommentContent(NoteContentDomainDto dto);

   CommentCreateResponseDto ofCommentCreate(NoteContentDomainDto dto);

   NoteCreateResponseDto ofNoteCreate(NoteContentDomainDto dto);

   @Mapping(source = "dto.owner.name", target = "ownerName")
   NoteSearchContentResponseDto ofFull(NoteFullDomainDto dto);

   NoteSearchTagsResponseDto ofTags(NoteTagsDomainDto dto);

   CommentEditResponseDto ofCommentEdit(NoteContentDomainDto dto);
}
