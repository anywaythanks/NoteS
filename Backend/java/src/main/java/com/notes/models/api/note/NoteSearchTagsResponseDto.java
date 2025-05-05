package com.notes.models.api.note;

import com.notes.models.api.tag.TagResponseDto;
import lombok.NonNull;

import java.time.Instant;
import java.util.List;

public record NoteSearchTagsResponseDto(@NonNull String description,
                                        @NonNull String title,
                                        @NonNull String path,
                                        @NonNull SyntaxTypeApiDto syntaxType,
                                        @NonNull NoteTypeApiDto noteType,
                                        boolean isPublic,
                                        List<TagResponseDto> tags,
                                        Instant createdOn)
        implements Description, Title, SyntaxType, NoteType, IsPublic, Path, CreatedOn, Tags {
}
