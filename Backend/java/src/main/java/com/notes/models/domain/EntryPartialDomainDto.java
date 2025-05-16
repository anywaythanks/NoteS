package com.notes.models.domain;

import lombok.NonNull;
import lombok.With;

import java.time.Instant;

public record EntryPartialDomainDto(@NonNull Long id,
                                    @NonNull String description,
                                    @NonNull String title,
                                    @NonNull String path,
                                    @With @NonNull String content,
                                    @NonNull AccountDomainDto owner,
                                    String mainPath,
                                    @With @NonNull SyntaxTypeDomainDto syntaxType,
                                    @NonNull EntryTypeDomainDto entryType,
                                    @NonNull StateDomainDto state,
                                    boolean isPublic,
                                    @NonNull Instant createdOn) {
}
