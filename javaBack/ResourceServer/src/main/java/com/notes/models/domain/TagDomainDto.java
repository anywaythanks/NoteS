package com.notes.models.domain;

import lombok.NonNull;

public record TagDomainDto(@NonNull Long id,
                           @NonNull String name,
                           @NonNull Integer color) {
}
