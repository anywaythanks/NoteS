package com.notes.models.api.note;

import lombok.NonNull;

public record NotePath(@NonNull String path) implements Path {
}
