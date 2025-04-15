package com.notes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaController {
   private final KafkaTemplate<String, NoteDto> kafkaTemplate;

   @PostMapping("/test1")
   public CompletableFuture<Map<String, String>> send() {
      return kafkaTemplate.send("NoteS", "Key1", new NoteDto("Title", "Content"))
              .thenApply(result -> {
                 Map<String, String> response = new HashMap<>();
                 response.put("topic", result.getRecordMetadata().topic());
                 response.put("partition", String.valueOf(result.getRecordMetadata().partition()));
                 response.put("offset", String.valueOf(result.getRecordMetadata().offset()));
                 return response;
              });
   }

   public record NoteDto(String title, String content) {
   }
}
