package com.notes.configs;

import com.notes.converters.SpringAddonsJwtAuthenticationUserConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig {
   @Bean
   SecurityFilterChain resourceServerSecurityFilterChain(
           HttpSecurity http,
           @Value("${scheme}") String scheme,
           @Value("${origins:[]}") String[] origins,
           SpringAddonsJwtAuthenticationUserConverter authenticationConverter,
           HandlerMappingIntrospector mvcHandlerMappingIntrospector)
           throws Exception {
      http.oauth2ResourceServer(resourceServer ->
              resourceServer.jwt(jwtConfigurer ->
                      jwtConfigurer.jwtAuthenticationConverter(authenticationConverter)));
      http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
      http.csrf(AbstractHttpConfigurer::disable);
      http.exceptionHandling(handeling -> handeling.authenticationEntryPoint((request, response, authException) -> {
         response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Restricted Content\"");
         response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
      }));
      http.authorizeHttpRequests((authorizeHttpRequests) ->
              authorizeHttpRequests
                      .requestMatchers(
                              new MvcRequestMatcher(mvcHandlerMappingIntrospector, "api/kafka/**"),
                              new MvcRequestMatcher(mvcHandlerMappingIntrospector, "resources/**"),
                              new MvcRequestMatcher(mvcHandlerMappingIntrospector, "swagger-ui"),
                              new MvcRequestMatcher(mvcHandlerMappingIntrospector, "swagger-ui/**"),
                              new MvcRequestMatcher(mvcHandlerMappingIntrospector, "v3/api-docs/**"),
                              new MvcRequestMatcher(mvcHandlerMappingIntrospector, "v3/api-docs"))
                      .permitAll()
                      .anyRequest().authenticated());
      if(Objects.equals(scheme, "https"))
         http.requiresChannel(channelRequestMatcherRegistry ->
                 channelRequestMatcherRegistry.anyRequest().requiresSecure());
      http.cors(cors -> {
         if(origins.length == 0) {
            cors.disable();
         } else {
            cors.configurationSource(corsConfig((List.of(origins))));
         }
      });
      return http.build();
   }

   CorsConfigurationSource corsConfig(List<String> allowedOrigins) {
      final var source = new UrlBasedCorsConfigurationSource();

      final var configuration = new CorsConfiguration();
      configuration.setAllowedOrigins(allowedOrigins);
      configuration.setAllowedMethods(List.of("*"));
      configuration.setAllowedHeaders(List.of("*"));
      configuration.setExposedHeaders(List.of("*"));

      source.registerCorsConfiguration("/**", configuration);
      return source;
   }
}
