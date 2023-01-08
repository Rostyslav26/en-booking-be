package com.website.enbookingbe.core.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.website.enbookingbe.core.security.jwt.JWTGrantedAuthoritiesConverter.AUTHORITIES_KEY;

@Slf4j
@Component
public class JWTProvider {

    // Token is valid 24 hours
    private static final long tokenValidityInMilliseconds = 86400L * 1000;
    private static final long tokenValidityInMillisecondsForRememberMe = 2592000L * 1000;

    private final Key key;
    private final JwtParser jwtParser;

    public JWTProvider(@Value("${website.security.authentication.jwt.base64-secret}") String base64Secret) {
        final byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createToken(@NotNull Authentication authentication, boolean rememberMe) {
        final List<String> authorities = JWTGrantedAuthoritiesConverter.convert(authentication.getAuthorities());

        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(createExpirationTokenDate(rememberMe))
            .compact();
    }

    public Authentication getAuthentication(String token) {
        final Claims claims = jwtParser.parseClaimsJws(token).getBody();

        final Collection<? extends GrantedAuthority> authorities = JWTGrantedAuthoritiesConverter.convert(claims);
        final UserDetails user = User.withUsername(claims.getSubject())
            .password(Strings.EMPTY)
            .authorities(authorities)
            .build();

        return new UsernamePasswordAuthenticationToken(user, token, authorities);
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
