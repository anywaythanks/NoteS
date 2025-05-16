package com.notes.models.domain;

import lombok.NonNull;

public record EntryEditDto(@NonNull String description,
                           @NonNull String content,
                           @NonNull String title,
                           @NonNull SyntaxTypeDomainDto syntaxType) {
}
