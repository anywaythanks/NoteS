package com.notes.models.dto.page;

import lombok.NonNull;

public record PageSizeDto(@NonNull Integer page) implements Page {
}
