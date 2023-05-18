package com.website.enbookingbe.security.jwt;

import com.website.enbookingbe.security.Principal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JWTProvider {

    // Token is valid 24 hours
    private static final long tokenValidityInMilliseconds = 86400L * 1000;
    private static final long tokenValidityInMillisecondsForRememberMe = 2592000L * 1000;

    private static final String USER_ID_KEY = "userId";
    private static final String USERNAME_KEY = "username";
    private static final String AUTHORITIES_KEY = "auth";

    private final Key key;
    private final JwtParser jwtParser;

    public JWTProvider(
        @Value("${website.security.authentication.jwt.base64-secret}") String base64Secret
    ) {
        final byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createToken(@NotNull Authentication authentication, boolean rememberMe) {
        final Principal principal = (Principal) authentication.getPrincipal();

        final String authorities = principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(USER_ID_KEY, principal.getId())
            .claim(USERNAME_KEY, principal.getUsername())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(createExpirationTokenDate(rememberMe))
            .compressWith(CompressionCodecs.DEFLATE)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        final Claims claims = jwtParser.parseClaimsJws(token).getBody();
        final Principal principal = getPrincipal(claims);

        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    private Principal getPrincipal(Claims claims) {
        final List<SimpleGrantedAuthority> authorities = Arrays
            .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .filter(auth -> !auth.trim().isEmpty())
            .map(SimpleGrantedAuthority::new)
            .toList();

        return Principal.builder()
            .id(Integer.parseInt(claims.get(USER_ID_KEY).toString()))
            .username(claims.get(USERNAME_KEY).toString())
            .password("")
            .authorities(authorities)
            .build();
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
