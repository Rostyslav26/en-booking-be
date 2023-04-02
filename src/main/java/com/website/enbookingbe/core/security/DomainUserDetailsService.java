package com.website.enbookingbe.core.security;

import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.core.user.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
@Slf4j
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String login) {
        log.debug("Authenticating {}", login);

        return userRepository.findByEmail(login)
            .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
    }
}
