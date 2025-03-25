package com.notes.models.dto.note;

import jakarta.validation.constraints.NotNull;

interface Query {
    @NotNull
    String query();
}
