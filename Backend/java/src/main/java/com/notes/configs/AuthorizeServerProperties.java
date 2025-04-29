package com.notes.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtClaimNames;

import java.io.Serial;
import java.net.URL;
import java.util.stream.Stream;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "auth-props")
public class AuthorizeServerProperties {
   private IssuerProperties[] issuers = {};

   public IssuerProperties get(URL issuerUri) throws MisconfigurationException {
      final var issuerProperties = Stream.of(issuers).filter(iss -> issuerUri.equals(iss.getUri())).toList();
      if(issuerProperties.isEmpty()) {
         throw new MisconfigurationException("Missing authorities mapping properties for %s".formatted(issuerUri.toString()));
      }
      if(issuerProperties.size() > 1) {
         throw new MisconfigurationException("Too many authorities mapping properties for %s".formatted(issuerUri.toString()));
      }
      return issuerProperties.getFirst();
   }

   @Setter
   @Getter
   public static class IssuerProperties {
      private URL uri;
      private ClaimMappingProperties[] claims;
      private String usernameJsonPath = JwtClaimNames.SUB;

      @Setter
      @Getter
      public static class ClaimMappingProperties {
         private String jsonPath;
         private CaseProcessing caseProcessing = CaseProcessing.UNCHANGED;
         private String prefix = "";

         enum CaseProcessing {
            UNCHANGED, TO_LOWER, TO_UPPER
         }
      }
   }

   static class MisconfigurationException extends RuntimeException {
      @Serial
      private static final long serialVersionUID = 5887967904749547431L;

      public MisconfigurationException(String msg) {
         super(msg);
      }
   }
}
