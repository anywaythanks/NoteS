package com.notes.models.dto.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

interface CreatedOn {
    @NotNull
    @JsonProperty("created_at")
    Instant createdOn();
}
