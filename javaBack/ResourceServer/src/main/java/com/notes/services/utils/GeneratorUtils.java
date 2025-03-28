package com.notes.services.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeneratorUtils {
   public UUID generateUUID() {
      return UUID.randomUUID();
   }
}
