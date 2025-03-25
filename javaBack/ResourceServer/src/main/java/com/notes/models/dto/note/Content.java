package com.notes.models.dto.note;

import jakarta.validation.constraints.NotNull;

interface Content {
    @NotNull
    String content();
}
