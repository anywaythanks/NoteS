package com.notes.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;


@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Document(indexName = "notes")
public class NoteContent {
   @Id
   UUID uuid;

   @Field(value = "content", type = FieldType.Text)
   @Setter
   String content;

   @Field(value = "title", type = FieldType.Text)
   @Setter
   String title;

   @Field(value = "score", type = FieldType.Keyword)
   BigDecimal score;

   @Field(value = "vec.vec", type = FieldType.Dense_Vector, dims = 312)
   double[] vec;

   @Field(value = "owner", type = FieldType.Keyword)
   Long owner;

   @Field(value = "entry_type", type = FieldType.Text)
   @Setter
   NoteType noteType;

   @Field(value = "syntax", type = FieldType.Text)
   @Setter
   SyntaxType syntaxType;
}
