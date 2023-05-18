package com.website.enbookingbe.core.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.website.enbookingbe.core.security.SecurityUtils.DEFAULT_AUDITING;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityUtils.getCurrentUser()
            .map(Principal::getUsername)
            .or(() -> Optional.of(DEFAULT_AUDITING));
    }
}
