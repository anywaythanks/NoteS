package com.notes.converters;

import com.notes.models.entity.State;
import com.notes.models.entity.SyntaxType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StateConverter implements AttributeConverter<State, Integer> {

   @Override
   public Integer convertToDatabaseColumn(State attribute) {
      return attribute.getId();
   }

   @Override
   public State convertToEntityAttribute(Integer dbData) {
      return State.valueOf(dbData);
   }
}
