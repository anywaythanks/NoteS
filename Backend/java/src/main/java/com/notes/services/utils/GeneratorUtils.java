package com.notes.services.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Utility service for generating unique identifiers.
 */
@Service
@RequiredArgsConstructor
public class GeneratorUtils {
   /**
    * Generates a random UUID using Java's UUID generator.
    *
    * @return A newly generated UUID
    */
   public UUID generateUUID() {
      return UUID.randomUUID();
   }
}
