package com.notes.models.dto.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.notes.models.NoteType;
import jakarta.validation.constraints.NotNull;

interface INoteType {
   @NotNull
   @JsonProperty("note_type")
   NoteType noteType();
}
