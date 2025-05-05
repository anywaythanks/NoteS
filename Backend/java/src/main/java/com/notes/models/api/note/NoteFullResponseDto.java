package com.notes.models.api.note;

import com.notes.models.api.tag.TagResponseDto;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record NoteFullResponseDto(@NonNull String description,
                                  @NonNull String title,
                                  @NonNull String path,
                                  @NonNull String content,
                                  @NonNull String ownerName,
                                  String mainPath,
                                  @NonNull SyntaxTypeApiDto syntaxType,
                                  @NonNull NoteTypeApiDto noteType,
                                  boolean isPublic,
                                  Instant createdOn,
                                  List<TagResponseDto> tags)
        implements Description, Content, Title, SyntaxType, NoteType, IsPublic, Path, CreatedOn, OwnerName, MainPath, Tags {
}
