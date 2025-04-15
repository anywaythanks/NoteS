package com.notes.services.utils;

import com.notes.controllers.KafkaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
   Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

   @KafkaListener(topics = "NoteS")
   public void listenObject(KafkaController.NoteDto note, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
      logger.debug("Received Note [{}]: {}", key, note.title());
   }
}