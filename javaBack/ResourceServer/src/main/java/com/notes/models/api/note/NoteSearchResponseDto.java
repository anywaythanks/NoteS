package com.notes.models.api.note;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

public record NoteSearchResponseDto(@NonNull String description,
                                    @NonNull String title,
                                    @NonNull String path,
                                    @NonNull SyntaxTypeApiDto syntaxType,
                                    @NonNull NoteTypeApiDto noteType,
                                    @NonNull BigDecimal score,
                                    boolean isPublic,
                                    Instant createdOn)
        implements Description, Title, SyntaxType, NoteType, IsPublic, Path, Score, CreatedOn {
}
