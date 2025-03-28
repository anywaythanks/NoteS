package com.notes.models.api.page;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

interface Limit {
   @Range(min = 5, max = 20)
   @NotNull
   Integer limit();
}
