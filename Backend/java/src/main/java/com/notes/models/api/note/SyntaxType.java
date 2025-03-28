package com.notes.models.api.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

interface SyntaxType {
   @NotNull
   @JsonProperty("syntax_name")
   SyntaxTypeApiDto syntaxType();
}
