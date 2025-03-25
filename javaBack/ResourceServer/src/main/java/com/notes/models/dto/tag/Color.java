package com.notes.models.dto.tag;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

interface Color {
   @NotNull
   @Range(min = 0, max = 0xFFFFFF)
   Integer color();
}
