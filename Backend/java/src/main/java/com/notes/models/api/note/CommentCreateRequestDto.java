package com.notes.models.api.note;

import lombok.NonNull;

public record CommentCreateRequestDto(@NonNull String description,
                                      @NonNull String content,
                                      @NonNull String title,
                                      @NonNull SyntaxTypeApiDto syntaxType) implements Content, Description, Title, SyntaxType {
}
