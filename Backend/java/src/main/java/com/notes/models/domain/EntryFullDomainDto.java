package com.notes.models.domain;

import lombok.NonNull;

import java.time.Instant;
import java.util.List;

public record EntryFullDomainDto(@NonNull Long id,
                                 @NonNull String description,
                                 @NonNull String title,
                                 @NonNull String path,
                                 @NonNull String content,
                                 @NonNull AccountDomainDto owner,
                                 String mainPath,
                                 @NonNull SyntaxTypeDomainDto syntaxType,
                                 @NonNull EntryTypeDomainDto entryType,
                                 @NonNull StateDomainDto state,
                                 boolean isPublic,
                                 @NonNull Instant createdOn,
                                 @NonNull List<TagDomainDto> tags) {
}
