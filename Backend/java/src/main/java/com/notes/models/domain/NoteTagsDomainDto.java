package com.notes.models.domain;

import lombok.NonNull;

import java.time.Instant;
import java.util.List;

public record NoteTagsDomainDto(@NonNull Long id,
                                @NonNull String description,
                                @NonNull String title,
                                @NonNull String path,
                                @NonNull AccountDomainDto owner,
                                @NonNull SyntaxTypeDomainDto syntaxType,
                                @NonNull EntryTypeDomainDto noteType,
                                @NonNull StateDomainDto state,
                                boolean isPublic,
                                @NonNull Instant createdOn,
                                @NonNull List<TagDomainDto> tags) {
}