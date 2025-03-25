package com.notes.models.dto.note;

import com.notes.models.dto.tag.TagResponseDto;
import com.notes.models.NoteType;
import com.notes.models.SyntaxType;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record NoteSearchContentResponseDto(@NonNull String description,
                                           @NonNull String title,
                                           @NonNull String path,
                                           @NonNull String ownerName,
                                           @NonNull String mainPath,
                                           @NonNull SyntaxType syntaxType,
                                           @NonNull NoteType noteType,
                                           @NonNull BigDecimal score,
                                           boolean isPublic,
                                           Instant createdOn,
                                           List<TagResponseDto> tags)
        implements Description, Title, ISyntaxType, INoteType, IsPublic, Path, Score, CreatedOn, OwnerName, MainPath, Tags {
}
