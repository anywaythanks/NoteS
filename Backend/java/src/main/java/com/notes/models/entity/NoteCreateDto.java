package com.notes.models.entity;

import lombok.NonNull;
import lombok.With;

import java.util.UUID;

public record NoteCreateDto(@NonNull String description,
                            @NonNull String title,
                            @NonNull String path,
                            @With @NonNull String content,
                            @NonNull UUID elasticUuid,
                            @NonNull Long ownerId,
                            Long mainId,
                            @With @NonNull SyntaxType syntaxType,
                            @NonNull NoteType noteType,
                            boolean isPublic) {
}
