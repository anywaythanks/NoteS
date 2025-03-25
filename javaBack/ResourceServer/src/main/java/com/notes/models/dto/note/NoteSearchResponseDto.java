package com.notes.models.dto.note;

import com.notes.models.NoteType;
import com.notes.models.SyntaxType;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

public record NoteSearchResponseDto(@NonNull String description,
                                    @NonNull String title,
                                    @NonNull String path,
                                    @NonNull SyntaxType syntaxType,
                                    @NonNull NoteType noteType,
                                    @NonNull BigDecimal score,
                                    boolean isPublic,
                                    Instant createdOn)
        implements Description, Title, ISyntaxType, INoteType, IsPublic, Path, Score, CreatedOn {
}
