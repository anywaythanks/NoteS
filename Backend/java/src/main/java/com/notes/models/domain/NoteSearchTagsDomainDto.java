package com.notes.models.domain;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record NoteSearchTagsDomainDto(@NonNull Long id,
                                      @NonNull String description,
                                      @NonNull String title,
                                      @NonNull String path,
                                      @NonNull AccountDomainDto owner,
                                      String mainPath,
                                      @NonNull SyntaxTypeDomainDto syntaxType,
                                      @NonNull EntryTypeDomainDto entryType,
                                      @NonNull StateDomainDto state,
                                      BigDecimal score,
                                      boolean isPublic,
                                      @NonNull Instant createdOn,
                                      @NonNull List<TagDomainDto> tags) {
}
