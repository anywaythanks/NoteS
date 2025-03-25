package com.notes.models.dto.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.notes.models.SyntaxType;
import jakarta.validation.constraints.NotNull;

interface ISyntaxType {
    @NotNull
    @JsonProperty("syntax_name")
    SyntaxType syntaxType();
}
