package com.notes.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "accounts")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
public class Account {
   @Id
   @GeneratedValue(strategy = SEQUENCE, generator = "account_seq")
   @SequenceGenerator(name = "account_seq", sequenceName = "account_id_seq")
   Long id;

   @NotNull
   @NotEmpty
   @Length(max = 128)
   @Column(name = "name", nullable = false, unique = true)
   String name;

   @NotNull
   @NotEmpty
   @Length(max = 128)
   @Column(name = "uuid", nullable = false, unique = true)
   String uuid;
}