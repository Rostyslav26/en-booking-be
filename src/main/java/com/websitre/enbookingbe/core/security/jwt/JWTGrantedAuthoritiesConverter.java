package com.websitre.enbookingbe.core.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class JWTGrantedAuthoritiesConverter {
    public static final String AUTHORITIES_KEY = "auth";

    @SuppressWarnings("unchecked")
    public static Collection<? extends GrantedAuthority> convert(Claims claims) {
        final Object authorities = claims.get(AUTHORITIES_KEY);
        if (isNull(authorities)) {
            return Collections.emptyList();
        }

        return ((List<String>) authorities).stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    public static List<String> convert(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    }
}
