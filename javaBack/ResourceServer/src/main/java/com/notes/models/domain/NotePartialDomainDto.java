package com.notes.models.domain;

import lombok.NonNull;

import java.time.Instant;

public record NotePartialDomainDto(@NonNull Long id,
                                   @NonNull String description,
                                   @NonNull String title,
                                   @NonNull String elasticUuid,
                                   @NonNull String path,
                                   @NonNull AccountDomainDto owner,
                                   @NonNull SyntaxTypeDomainDto syntaxType,
                                   @NonNull NoteTypeDomainDto noteType,
                                   boolean isPublic,
                                   @NonNull Instant createdOn) {
}