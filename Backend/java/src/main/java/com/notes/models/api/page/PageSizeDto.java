package com.notes.models.api.page;

import lombok.NonNull;

public record PageSizeDto(@NonNull Integer page) implements Page {
}
