package com.notes.models.domain;

import lombok.NonNull;

public record AccountDomainDto(@NonNull Long id, @NonNull String uuid, @NonNull String name) {
}
