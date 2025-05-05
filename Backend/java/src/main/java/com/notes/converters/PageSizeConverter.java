package com.notes.converters;


import com.notes.models.api.page.PageSizeDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PageSizeConverter implements Converter<String, PageSizeDto> {
   @Override
   public PageSizeDto convert(@NonNull String source) {
      return new PageSizeDto(Integer.parseInt(source));
   }
}