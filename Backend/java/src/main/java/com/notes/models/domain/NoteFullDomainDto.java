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
                                @With @NonNull String content,
                                @NonNull AccountDomainDto owner,
                                String mainPath,
                                @With @NonNull SyntaxTypeDomainDto syntaxType,
                                @NonNull NoteTypeDomainDto noteType,
                                @NonNull StateDomainDto state,
                                BigDecimal score,
                                boolean isPublic,
                                @NonNull Instant createdOn,
                                @NonNull List<TagDomainDto> tags) {
}
