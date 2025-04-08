package com.notes.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "tags")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
public class Tag {
   @Id
   @GeneratedValue(strategy = IDENTITY)//TODO: бд должна сама сгенерить, мб не робит стратегия и будут попытки в генерацию
   Long id;

   @NotNull
   @NotEmpty
   @Length(max = 128)
   @Column(name = "name", nullable = false, unique = true)
   String name;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "account_id", nullable = false)
   Account owner;

   @NotNull
   @NotEmpty
   @Range(min = 0, max = 0xFFFFFF)
   @Column(name = "color", nullable = false)
   Integer color;
}