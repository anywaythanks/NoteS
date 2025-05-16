package com.notes.models.entity;

import lombok.NonNull;
import lombok.With;

public record EntryEditDto(@NonNull String description,
                           @NonNull String title,
                           @With @NonNull String content,
                           @With @NonNull SyntaxType syntaxType,
                           @NonNull EntryType entryType) {
}
