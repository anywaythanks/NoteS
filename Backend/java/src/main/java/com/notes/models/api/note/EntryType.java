package com.notes.models.api.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

interface EntryType {
   @NotNull
   @JsonProperty("note_type")
   EntryTypeApiDto entryType();
}
