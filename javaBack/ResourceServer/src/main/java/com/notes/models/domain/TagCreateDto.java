package com.notes.models.domain;

import lombok.NonNull;

public record TagCreateDto(@NonNull String name,
                           @NonNull Integer color) {
}
