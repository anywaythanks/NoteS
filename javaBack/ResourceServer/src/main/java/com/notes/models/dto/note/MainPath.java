package com.notes.models.dto.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

interface MainPath {
    @NotNull
    @JsonProperty("main_note_path")
    String mainPath();
}
