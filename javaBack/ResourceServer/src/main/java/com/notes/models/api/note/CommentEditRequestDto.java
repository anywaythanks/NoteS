package com.notes.models.api.note;

import lombok.NonNull;

public record CommentEditRequestDto(@NonNull String content,
                                    @NonNull String title,
                                    @NonNull SyntaxTypeApiDto syntaxType) implements Content, Title, SyntaxType {
}
