package com.notes.models.domain;

import lombok.NonNull;

public record NoteEditOnlyContentDto(@NonNull SyntaxTypeDomainDto syntaxType,
                                     @NonNull String content) {
}
