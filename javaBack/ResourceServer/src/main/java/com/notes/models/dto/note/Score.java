package com.notes.models.dto.note;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

interface Score {
    @NotNull
    BigDecimal score();
}
