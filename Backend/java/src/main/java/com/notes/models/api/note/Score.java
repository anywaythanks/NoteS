package com.notes.models.api.note;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

interface Score {
   @NotNull
   BigDecimal score();
}
