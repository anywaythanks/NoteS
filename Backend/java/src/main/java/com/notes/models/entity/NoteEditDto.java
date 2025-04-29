package com.notes.models.entity;

import lombok.NonNull;
import lombok.With;

import java.util.UUID;

public record NoteEditDto(@NonNull String description,
                          @NonNull String title,
                          @With @NonNull String content,
                          @With @NonNull SyntaxType syntaxType,
                          @NonNull NoteType noteType) {
}
