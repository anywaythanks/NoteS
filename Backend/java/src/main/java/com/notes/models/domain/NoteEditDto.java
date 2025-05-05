package com.notes.models.domain;

import com.notes.models.entity.NoteType;
import lombok.NonNull;

public record NoteEditDto(@NonNull String description,
                          @NonNull String content,
                          @NonNull String title,
                          @NonNull SyntaxTypeDomainDto syntaxType) {
}
