package com.notes.models.api.note;

import lombok.NonNull;

public record CommentCreateResponseDto(@NonNull String title,
                                       @NonNull String path,
                                       @NonNull String content,
                                       @NonNull SyntaxTypeApiDto syntaxType,
                                       @NonNull NoteTypeApiDto noteType,
                                       boolean isPublic)
        implements Content, Title, SyntaxType, NoteType, IsPublic, Path {
}
