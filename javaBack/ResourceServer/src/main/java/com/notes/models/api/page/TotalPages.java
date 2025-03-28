package com.notes.models.api.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

interface TotalPages {
   @PositiveOrZero
   @NotNull
   @JsonProperty("total_pages")
   Integer totalPages();
}
