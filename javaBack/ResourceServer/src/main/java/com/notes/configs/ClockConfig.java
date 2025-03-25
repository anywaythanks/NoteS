package com.notes.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfig {
    @Bean
    Clock actualClock() {
        return Clock.systemUTC();
    }
}
