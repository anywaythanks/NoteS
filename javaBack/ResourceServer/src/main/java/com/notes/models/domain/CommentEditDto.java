package com.notes.models.domain;

import lombok.NonNull;

public record CommentEditDto(@NonNull String content,
                             @NonNull String title,
                             @NonNull SyntaxTypeDomainDto syntaxType) {
}
