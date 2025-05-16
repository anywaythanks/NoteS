package com.notes.models.api.note;

import lombok.NonNull;

public record EntryCreateResponseDto(@NonNull String description,
                                     @NonNull String content,
                                     @NonNull String title,
                                     @NonNull String path,
                                     @NonNull SyntaxTypeApiDto syntaxType,
                                     @NonNull EntryTypeApiDto entryType,
                                     boolean isPublic) implements Content, Description, Title, SyntaxType, EntryType, IsPublic, Path {
}
