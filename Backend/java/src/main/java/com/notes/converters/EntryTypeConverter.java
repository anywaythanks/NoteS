package com.notes.converters;

import com.notes.models.entity.EntryType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EntryTypeConverter implements AttributeConverter<EntryType, Integer> {

   @Override
   public Integer convertToDatabaseColumn(EntryType attribute) {
      return attribute.getId();
   }

   @Override
   public EntryType convertToEntityAttribute(Integer dbData) {
      return EntryType.valueOf(dbData);
   }
}
