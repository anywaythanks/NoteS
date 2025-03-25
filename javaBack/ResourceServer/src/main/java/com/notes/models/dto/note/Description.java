package com.notes.models.dto.note;

import jakarta.validation.constraints.NotNull;

interface Description {
    @NotNull
    String description();
}
