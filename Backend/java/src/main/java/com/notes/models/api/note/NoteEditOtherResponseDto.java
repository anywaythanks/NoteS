package com.notes.models.api.note;

import lombok.NonNull;

public record NoteEditOtherResponseDto(@NonNull String description,
                                       @NonNull String title,
                                       @NonNull SyntaxTypeApiDto syntaxType) implements Description, Title, SyntaxType {
}
