package com.notes.models.dto.note;

import lombok.NonNull;

public record NotePath(@NonNull String path) implements Path {
}
