package com.notes.models.api.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

interface NoteType {
   @NotNull
   @JsonProperty("note_type")
   NoteTypeApiDto noteType();
}
