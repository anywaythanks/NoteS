package com.notes.services.managers;

import com.notes.mappers.repository.EntryRepositoryMapper;
import com.notes.mappers.repository.PageRepositoryMapper;
import com.notes.models.domain.EntryPartialDomainDto;
import com.notes.models.domain.PageDomainDto;
import com.notes.repository.EntryRepositoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service for retrieving comment information and paginated results.
 * Handles comment data presentation and pagination mapping.
 */
@Service
@RequiredArgsConstructor
public class CommentInformationService {
   private final AccountInformationService accountInformationService;
   private final EntryRepositoryQuery entryRepositoryQuery;
   private final PageRepositoryMapper pageMapper;
   private final EntryRepositoryMapper noteMapper;
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
   public PageDomainDto<EntryPartialDomainDto> comments(String accountName, String notePath, Integer pageNum, Integer limit) {
      var note = noteInformationService.findPublicByPath(notePath, accountName);
      var page = entryRepositoryQuery
              .findPublicParents(note.id(), PageRequest.of(pageNum, limit));
      return pageMapper.of(page.map(noteMapper::ofPartial));
   }
}
