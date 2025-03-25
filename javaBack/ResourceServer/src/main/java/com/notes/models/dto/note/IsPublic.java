package com.notes.models.dto.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

interface IsPublic {
    @NotNull
    @JsonProperty("is_public")
    boolean isPublic();
}
