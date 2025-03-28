package com.notes.models.domain;

import lombok.NonNull;

import java.time.Instant;
import java.util.List;

public record NoteTagsDomainDto(@NonNull Long id,
                                @NonNull String description,
                                @NonNull String title,
                                @NonNull String path,
                                @NonNull AccountDomainDto owner,
                                @NonNull String elasticUuid,
                                @NonNull SyntaxTypeDomainDto syntaxType,
                                @NonNull NoteTypeDomainDto noteType,
                                boolean isPublic,
                                @NonNull Instant createdOn,
                                @NonNull List<TagDomainDto> tags) {
}