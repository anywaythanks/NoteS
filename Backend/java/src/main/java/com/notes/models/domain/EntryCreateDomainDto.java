package com.notes.models.domain;

import lombok.NonNull;

public record EntryCreateDomainDto(@NonNull String description,
                                   @NonNull String content,
                                   @NonNull String title,
                                   @NonNull SyntaxTypeDomainDto syntaxType) {
}
