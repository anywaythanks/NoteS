package com.notes.models.domain;

import lombok.NonNull;

import java.time.Instant;

public record EntryMinimalDomainDto(@NonNull Long id,
                                    @NonNull String path,
                                    @NonNull AccountDomainDto owner,
                                    @NonNull EntryTypeDomainDto entryType,
                                    @NonNull StateDomainDto state,
                                    boolean isPublic,
                                    @NonNull Instant createdOn) {
}