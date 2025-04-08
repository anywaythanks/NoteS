package com.notes.services.managers;

import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.mappers.repository.PageRepositoryMapper;
import com.notes.models.domain.NoteContentDomainDto;
import com.notes.models.domain.PageDomainDto;
import com.notes.repository.NoteRepository;
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
   private final NoteRepository noteRepository;
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
   public PageDomainDto<NoteContentDomainDto> comments(String accountName, String notePath, Integer pageNum, Integer limit) {
      var note = noteInformationService.findPublicByPath(accountName, notePath);
      var page = noteRepository
              .findComments(note.id(), PageRequest.of(pageNum, limit))
              .map(noteMapper::of);
      return pageMapper.of(page);
   }
}
