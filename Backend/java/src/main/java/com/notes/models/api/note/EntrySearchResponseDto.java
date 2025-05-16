package com.notes.models.api.note;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

public record EntrySearchResponseDto(@NonNull String description,
                                     @NonNull String title,
                                     @NonNull String path,
                                     @NonNull SyntaxTypeApiDto syntaxType,
                                     @NonNull EntryTypeApiDto entryType,
                                     @NonNull BigDecimal score,
                                     boolean isPublic,
                                     Instant createdOn)
        implements Description, Title, SyntaxType, EntryType, IsPublic, Path, Score, CreatedOn {
}
