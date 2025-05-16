package com.notes.models.api.note;

import com.notes.models.api.tag.TagResponseDto;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record EntrySearchTagsResponseDto(@NonNull String description,
                                         @NonNull String title,
                                         @NonNull String path,
                                         @NonNull String ownerName,
                                         @NonNull SyntaxTypeApiDto syntaxType,
                                         @NonNull EntryTypeApiDto entryType,
                                         boolean isPublic,
                                         BigDecimal score,
                                         List<TagResponseDto> tags,
                                         Instant createdOn)
        implements Description, Title, OwnerName, SyntaxType, Score, EntryType, IsPublic, Path, CreatedOn, Tags {
}
