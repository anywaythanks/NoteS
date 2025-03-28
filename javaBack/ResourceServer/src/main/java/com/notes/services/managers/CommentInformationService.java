package com.notes.services.managers;

import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.mappers.repository.PageRepositoryMapper;
import com.notes.models.domain.NoteContentDomainDto;
import com.notes.models.domain.PageDomainDto;
import com.notes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentInformationService {
   private final AccountInformationService accountInformationService;
   private final NoteRepository noteRepository;
   private final PageRepositoryMapper pageMapper;
   private final NoteRepositoryMapper noteMapper;
   private final NoteInformationService noteInformationService;

   public PageDomainDto<NoteContentDomainDto> comments(String accountName, String notePath, Integer pageNum, Integer limit) {
      var note = noteInformationService.findPublicByPath(accountName, notePath);
      var page = noteRepository
              .findComments(note.id(), PageRequest.of(pageNum, limit))
              .map(noteMapper::of);
      return pageMapper.of(page);
   }
}
