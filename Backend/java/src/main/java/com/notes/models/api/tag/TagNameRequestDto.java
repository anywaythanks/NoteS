package com.notes.models.api.tag;

import lombok.NonNull;

public record TagNameRequestDto(@NonNull String name)
        implements Name {
}
