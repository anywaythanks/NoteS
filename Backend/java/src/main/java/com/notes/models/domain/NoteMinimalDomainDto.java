package com.notes.models.domain;

import lombok.NonNull;

import java.time.Instant;

public record NoteMinimalDomainDto(@NonNull Long id,
                                   @NonNull String path,
                                   @NonNull AccountDomainDto owner,
                                   @NonNull NoteTypeDomainDto noteType,
                                   @NonNull StateDomainDto state,
                                   boolean isPublic,
                                   @NonNull Instant createdOn) {
}