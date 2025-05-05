package com.notes.converters;


import com.notes.models.api.page.PageLimitDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PageLimitConverter implements Converter<String, PageLimitDto> {
   @Override
   public PageLimitDto convert(@NonNull String source) {
      return new PageLimitDto(Integer.parseInt(source));
   }
}