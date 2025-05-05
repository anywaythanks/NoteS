package com.notes.models.api.note;

import lombok.NonNull;

public record NoteEditRequestDto(@NonNull String description,
                                 @NonNull String title,
                                 @NonNull String content,
                                 @NonNull SyntaxTypeApiDto syntaxType) implements Title, Description, Content, SyntaxType {
}
