package com.notes.configs;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarkdownConfig {

   @Bean
   public Parser markdownParser() {
      return Parser.builder()
              .build();
   }

   @Bean
   public TextContentRenderer textRenderer() {
      return TextContentRenderer.builder()
              .build();
   }
}
