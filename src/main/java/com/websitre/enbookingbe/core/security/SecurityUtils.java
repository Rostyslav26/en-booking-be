package com.websitre.enbookingbe.core.security;

import com.websitre.enbookingbe.core.user.management.domain.Authority;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.SecureRandom;
import java.util.Optional;

import static java.util.Objects.isNull;

public class SecurityUtils {
    public static final String DEFAULT_AUDITING = "SYSTEM";

    private static final int DEF_COUNT = 20;
    private static final SecureRandom SECURE_RANDOM;

    static {
        SECURE_RANDOM = new SecureRandom();
        SECURE_RANDOM.nextBytes(new byte[64]);
    }

    private SecurityUtils() {
    }

    public static Optional<String> getCurrentUserLogin() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    public static Authority toAuthority(GrantedAuthority grantedAuthority) {
        final Authority authority = new Authority();
        authority.setId(grantedAuthority.getAuthority());

        return authority;
    }

    public static String generateRandomAlphanumericString() {
        return RandomStringUtils.random(DEF_COUNT, 0, 0, true, true, null, SECURE_RANDOM);
    }

    public static String generatePassword() {
        return generateRandomAlphanumericString();
    }

    public static String generateActivationKey() {
        return generateRandomAlphanumericString();
    }

    public static String generateResetKey() {
        return generateRandomAlphanumericString();
    }

    private static String extractPrincipal(Authentication authentication) {
        if (isNull(authentication)) {
            return null;
        }

        final Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails user) {
            return user.getUsername();
        } else if (principal instanceof String user) {
            return user;
        }

        return null;
    }
}
