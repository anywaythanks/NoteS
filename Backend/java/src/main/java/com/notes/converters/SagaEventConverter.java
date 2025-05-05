package com.notes.converters;

import com.notes.models.entity.SagaEvent;
import com.notes.models.entity.SyntaxType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SagaEventConverter implements AttributeConverter<SagaEvent, Integer> {

   @Override
   public Integer convertToDatabaseColumn(SagaEvent attribute) {
      return attribute.getId();
   }

   @Override
   public SagaEvent convertToEntityAttribute(Integer dbData) {
      return SagaEvent.valueOf(dbData);
   }
}
