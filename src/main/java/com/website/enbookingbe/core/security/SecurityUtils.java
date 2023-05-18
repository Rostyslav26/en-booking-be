package com.website.enbookingbe.core.security;

import com.website.enbookingbe.core.user.management.exception.UserNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static Optional<Principal> getCurrentUser() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();

        if (isNull(authentication)) {
            return Optional.empty();
        }

        final Object principal = authentication.getPrincipal();
        if (principal instanceof Principal user) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    public static Principal getCurrentUserOrThrow() {
        return getCurrentUser().orElseThrow(() -> new UserNotFoundException("User not found"));
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
}
