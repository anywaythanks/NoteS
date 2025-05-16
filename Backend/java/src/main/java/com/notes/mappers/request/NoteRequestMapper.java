package com.notes.mappers.request;

import com.notes.configs.MapstructConfig;
import com.notes.models.api.note.CommentCreateRequestDto;
import com.notes.models.api.note.CommentEditRequestDto;
import com.notes.models.api.note.NoteCreateRequestDto;
import com.notes.models.api.note.NoteEditOnlyContentRequestDto;
import com.notes.models.api.note.NoteEditRequestDto;
import com.notes.models.api.note.NoteEditPublicRequestDto;
import com.notes.models.api.note.SyntaxTypeApiDto;
import com.notes.models.domain.CommentEditDto;
import com.notes.models.domain.EntryCreateDomainDto;
import com.notes.models.domain.EntryEditDto;
import com.notes.models.domain.EntryEditOnlyContentDto;
import com.notes.models.domain.EntryPublicDto;
import com.notes.models.domain.SyntaxTypeDomainDto;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface NoteRequestMapper {
   SyntaxTypeDomainDto of(SyntaxTypeApiDto api);

   EntryPublicDto of(NoteEditPublicRequestDto dto);

   EntryEditDto of(NoteEditRequestDto dto);

   EntryEditOnlyContentDto of(NoteEditOnlyContentRequestDto dto);

   EntryCreateDomainDto of(CommentCreateRequestDto dto);

   EntryCreateDomainDto of(NoteCreateRequestDto dto);

   CommentEditDto of(CommentEditRequestDto dto);

}
