package com.notes.models.dto.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

interface Total {
    @PositiveOrZero
    @NotNull
    @JsonProperty("total_pages")
    Integer total();
}
