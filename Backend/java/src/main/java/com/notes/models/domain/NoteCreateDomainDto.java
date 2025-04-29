package com.notes.models.domain;

import lombok.NonNull;

public record NoteCreateDomainDto(@NonNull String description,
                                  @NonNull String content,
                                  @NonNull String title,
                                  @NonNull SyntaxTypeDomainDto syntaxType) {
}
