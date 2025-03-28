package com.notes.models.domain;

import lombok.NonNull;

public record NoteCreateDto(@NonNull String description,
                            @NonNull String content,
                            @NonNull String title,
                            @NonNull SyntaxTypeDomainDto syntaxType) {
}
