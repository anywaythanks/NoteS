package com.notes.models.domain;

import lombok.NonNull;

public record EntryEditOnlyContentDto(@NonNull SyntaxTypeDomainDto syntaxType,
                                      @NonNull String content) {
}
