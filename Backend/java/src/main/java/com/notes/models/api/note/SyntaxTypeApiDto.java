package com.notes.models.api.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum SyntaxTypeApiDto {
   @JsonProperty("PLAINTEXT")
   PLAINTEXT,
   @JsonProperty("MARKDOWN")
   MARKDOWN
}
