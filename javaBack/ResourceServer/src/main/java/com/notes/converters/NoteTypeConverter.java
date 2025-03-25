package com.notes.converters;

import com.notes.models.NoteType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class NoteTypeConverter implements AttributeConverter<NoteType, Integer> {

   @Override
   public Integer convertToDatabaseColumn(NoteType attribute) {
      return attribute.getId();
   }

   @Override
   public NoteType convertToEntityAttribute(Integer dbData) {
      return NoteType.valueOf(dbData);
   }
}
