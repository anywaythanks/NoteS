package com.notes.mappers.request;

import com.notes.configs.MapstructConfig;
import com.notes.models.api.note.CommentCreateRequestDto;
import com.notes.models.api.note.CommentEditRequestDto;
import com.notes.models.api.note.NoteCreateRequestDto;
import com.notes.models.api.note.NoteEditOnlyContentRequestDto;
import com.notes.models.api.note.NoteEditOtherRequestDto;
import com.notes.models.api.note.NoteEditPublicRequestDto;
import com.notes.models.api.note.SyntaxTypeApiDto;
import com.notes.models.domain.CommentEditDto;
import com.notes.models.domain.NoteCreateDomainDto;
import com.notes.models.domain.NoteEditOnlyContentDto;
import com.notes.models.domain.NoteEditOtherDto;
import com.notes.models.domain.NotePublicDto;
import com.notes.models.domain.SyntaxTypeDomainDto;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface NoteRequestMapper {
   SyntaxTypeDomainDto of(SyntaxTypeApiDto api);

   NotePublicDto of(NoteEditPublicRequestDto dto);

   NoteEditOtherDto of(NoteEditOtherRequestDto dto);

   NoteEditOnlyContentDto of(NoteEditOnlyContentRequestDto dto);

   NoteCreateDomainDto of(CommentCreateRequestDto dto);

   NoteCreateDomainDto of(NoteCreateRequestDto dto);

   CommentEditDto of(CommentEditRequestDto dto);

}
