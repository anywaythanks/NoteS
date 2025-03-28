package com.notes.models.api.note;

import lombok.NonNull;

public record NoteCreateResponseDto(@NonNull String description,
                                    @NonNull String content,
                                    @NonNull String title,
                                    @NonNull String path,
                                    @NonNull SyntaxTypeApiDto syntaxType,
                                    @NonNull NoteTypeApiDto noteType,
                                    boolean isPublic) implements Content, Description, Title, SyntaxType, NoteType, IsPublic, Path {
}
