package com.website.enbookingbe.core.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.website.enbookingbe.core.user.management.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JWTProvider {

    // Token is valid 24 hours
    private static final long tokenValidityInMilliseconds = 86400L * 1000;
    private static final long tokenValidityInMillisecondsForRememberMe = 2592000L * 1000;
    private static final String USER = "user";

    private final Key key;
    private final JwtParser jwtParser;
    private final ObjectMapper objectMapper;

    public JWTProvider(
        @Value("${website.security.authentication.jwt.base64-secret}") String base64Secret,
        ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
        final byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createToken(@NotNull Authentication authentication, boolean rememberMe) {
        final User user = (User) authentication.getPrincipal();
        user.setPassword(Strings.EMPTY);

        try {
            final String userJson = objectMapper.writeValueAsString(user);

            return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("user", userJson)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(createExpirationTokenDate(rememberMe))
                .compact();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while creating token", e);
        }
    }

    public Authentication getAuthentication(String token) {
        final Claims claims = jwtParser.parseClaimsJws(token).getBody();
        final String userJson = claims.get(USER, String.class);

        try {
            final User user = objectMapper.readValue(userJson, User.class);
            return new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while parsing token", e);
        }
    }

    public boolean validateToken(final String token) {
        try {
            jwtParser.parseClaimsJws(token);

            return true;
        } catch (SignatureException ex) {
            log.trace("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            log.trace("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            log.trace("Expired JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            log.trace("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            log.trace("JWT claims string is empty.", ex);
        }

        return false;
    }

    private Date createExpirationTokenDate(boolean rememberMe) {
        final long now = (new Date()).getTime();

        return rememberMe
            ? new Date(now + tokenValidityInMillisecondsForRememberMe)
            : new Date(now + tokenValidityInMilliseconds);
    }
}
