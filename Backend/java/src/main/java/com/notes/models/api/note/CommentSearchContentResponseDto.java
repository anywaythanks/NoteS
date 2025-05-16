package com.notes.models.api.note;

import lombok.NonNull;

import java.time.Instant;

public record CommentSearchContentResponseDto(@NonNull String description,
                                              @NonNull String title,
                                              @NonNull String path,
                                              @NonNull String content,
                                              @NonNull String ownerName,
                                              @NonNull String mainPath,
                                              @NonNull SyntaxTypeApiDto syntaxType,
                                              @NonNull EntryTypeApiDto entryType,
                                              @NonNull StateApiDto state,
                                              boolean isPublic,
                                              Instant createdOn)
        implements Description, Content, State, Title, SyntaxType, EntryType, IsPublic, Path, CreatedOn, OwnerName, MainPath {
}
