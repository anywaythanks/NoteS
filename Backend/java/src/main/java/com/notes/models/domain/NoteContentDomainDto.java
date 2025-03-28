package com.notes.models.domain;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

public record NoteContentDomainDto(@NonNull Long id,
                                   @NonNull String description,
                                   @NonNull String title,
                                   @NonNull String path,
                                   @NonNull String content,
                                   @NonNull String elasticUuid,
                                   @NonNull AccountDomainDto owner,
                                   @NonNull String mainPath,
                                   @NonNull SyntaxTypeDomainDto syntaxType,
                                   @NonNull NoteTypeDomainDto noteType,
                                   @NonNull BigDecimal score,
                                   boolean isPublic,
                                   @NonNull Instant createdOn) {
}
