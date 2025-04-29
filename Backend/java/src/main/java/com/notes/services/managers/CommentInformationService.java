package com.notes.services.managers;

import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.mappers.repository.PageRepositoryMapper;
import com.notes.models.domain.NoteSearchDomainDto;
import com.notes.models.domain.PageDomainDto;
import com.notes.repository.NoteRepositoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service for retrieving comment information and paginated results.
 * Handles comment data presentation and pagination mapping.
 */
@Service
@RequiredArgsConstructor
public class CommentInformationService {
   private final AccountInformationService accountInformationService;
   private final NoteRepositoryQuery noteRepositoryQuery;
   private final PageRepositoryMapper pageMapper;
   private final NoteRepositoryMapper noteMapper;
   private final NoteInformationService noteInformationService;

   /**
    * Retrieves paginated comments for a specific note.
    *
    * @param accountName Name of the account accessing the comments
    * @param notePath    Unique path identifier of the parent note
    * @param pageNum     Pagination page number (0-based)
    * @param limit       Number of results per page
    * @return PageDomainDto containing paginated NoteContentDomainDto comments
    */
   public PageDomainDto<NoteSearchDomainDto> comments(String accountName, String notePath, Integer pageNum, Integer limit) {
      var note = noteInformationService.findPublicByPath(accountName, notePath);
      var page = noteRepositoryQuery
              .findComments(note.id(), PageRequest.of(pageNum, limit))
              .map(n -> noteMapper.of(n, BigDecimal.ONE))
              .map(noteMapper::of);
      return pageMapper.of(page);
   }
}
