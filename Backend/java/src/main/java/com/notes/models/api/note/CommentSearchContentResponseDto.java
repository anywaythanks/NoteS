package com.notes.models.api.note;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

public record CommentSearchContentResponseDto(@NonNull String description,
                                              @NonNull String title,
                                              @NonNull String path,
                                              @NonNull String content,
                                              @NonNull String ownerName,
                                              @NonNull String mainPath,
                                              @NonNull SyntaxTypeApiDto syntaxType,
                                              @NonNull NoteTypeApiDto noteType,
                                              boolean isPublic,
                                              Instant createdOn)
        implements Description, Content, Title, SyntaxType, NoteType, IsPublic, Path, CreatedOn, OwnerName, MainPath {
}
