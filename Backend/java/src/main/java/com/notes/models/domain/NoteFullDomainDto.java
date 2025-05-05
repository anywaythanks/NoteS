package com.notes.models.domain;

import lombok.NonNull;
import lombok.With;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record NoteFullDomainDto(@NonNull Long id,
                                @NonNull String description,
                                @NonNull String title,
                                @NonNull String path,
                                @NonNull String content,
                                @NonNull AccountDomainDto owner,
                                String mainPath,
                                @NonNull SyntaxTypeDomainDto syntaxType,
                                @NonNull NoteTypeDomainDto noteType,
                                @NonNull StateDomainDto state,
                                boolean isPublic,
                                @NonNull Instant createdOn,
                                @NonNull List<TagDomainDto> tags) {
}
