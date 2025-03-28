package com.notes.converters;

import com.notes.models.entity.SyntaxType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SyntaxTypeConverter implements AttributeConverter<SyntaxType, Integer> {

   @Override
   public Integer convertToDatabaseColumn(SyntaxType attribute) {
      return attribute.getId();
   }

   @Override
   public SyntaxType convertToEntityAttribute(Integer dbData) {
      return SyntaxType.valueOf(dbData);
   }
}
