package com.notes.models.entity;

import java.math.BigDecimal;

public record EntryScored(Entry entry, BigDecimal score) {
}
