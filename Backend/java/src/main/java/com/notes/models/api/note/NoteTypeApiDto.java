package com.notes.models.api.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum NoteTypeApiDto {
   @JsonProperty("NOTE")
   NOTE,
   @JsonProperty("COMMENT")
   COMMENT,
   @JsonProperty("COMMENT_REDACTED")
   COMMENT_REDACTED
}
