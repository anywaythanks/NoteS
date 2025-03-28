package com.notes.models.domain;

import lombok.NonNull;

import java.util.List;
import java.util.function.Function;

public record PageDomainDto<T>(@NonNull List<T> items,
                               @NonNull Integer totalPages,
                               @NonNull Long totalElements,
                               @NonNull Integer page) {

   public <U> PageDomainDto<U> map(Function<? super T, U> mapper) {
      return new PageDomainDto<>(items.stream().map(mapper).toList(), totalPages, totalElements, page);
   }
}
