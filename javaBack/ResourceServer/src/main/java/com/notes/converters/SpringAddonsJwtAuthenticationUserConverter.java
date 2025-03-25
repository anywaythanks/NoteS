package com.notes.converters;

import com.notes.configs.AuthorizeServerProperties;
import com.notes.models.auth.AuthorizationTokenUser;
import com.notes.models.auth.UserPrincipal;
import com.jayway.jsonpath.JsonPath;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SpringAddonsJwtAuthenticationUserConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final AuthorizeServerProperties springAddonsProperties;

    public SpringAddonsJwtAuthenticationUserConverter(AuthorizeServerProperties springAddonsProperties) {
        this.springAddonsProperties = springAddonsProperties;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        final var issuerProperties = springAddonsProperties.get(jwt.getIssuer());
        final var authorities = new JwtGrantedAuthoritiesConverter(issuerProperties).convert(jwt);
        final String username = JsonPath.read(jwt.getClaims(), issuerProperties.getUsernameJsonPath());
        final String uuid = jwt.getSubject();
        final var userPrincipal = new UserPrincipal(uuid, username, authorities);
        return new AuthorizationTokenUser(userPrincipal, jwt);
    }
}
