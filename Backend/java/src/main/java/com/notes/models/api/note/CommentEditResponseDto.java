package com.notes.models.api.note;

import lombok.NonNull;

public record CommentEditResponseDto(@NonNull String title,
                                     @NonNull String path,
                                     @NonNull String content,
                                     @NonNull SyntaxTypeApiDto syntaxType,
                                     @NonNull EntryTypeApiDto entryType,
                                     boolean isPublic)
        implements Content, Title, SyntaxType, EntryType, IsPublic, Path {
}
