package com.notes.models.api.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum EntryTypeApiDto {
   @JsonProperty("NOTE")
   NOTE,
   @JsonProperty("COMMENT")
   COMMENT
}
