package com.notes.models.api.note;

import lombok.NonNull;

public record NoteEditOnlyContentRequestDto(@NonNull SyntaxTypeApiDto syntaxType,
                                            @NonNull String content) implements Content, SyntaxType {
}
