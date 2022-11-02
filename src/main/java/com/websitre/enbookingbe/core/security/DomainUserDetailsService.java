package com.websitre.enbookingbe.core.security;

import com.websitre.enbookingbe.core.user.management.domain.User;
import com.websitre.enbookingbe.core.user.management.exception.UserNotActivatedException;
import com.websitre.enbookingbe.core.user.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component("userDetailsService")
@Slf4j
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final EmailValidator emailValidator = new EmailValidator();

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {
        log.debug("Authenticating {}", login);

        if (emailValidator.isValid(login, null)) {
            return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(login)
                .map(user -> createSpringSecurityUser(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }

        final String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);

        return userRepository.findOneWithAuthoritiesByUsername(lowercaseLogin)
            .map(user -> createSpringSecurityUser(lowercaseLogin, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String login, User user) {
        if (!user.activated()) {
            throw new UserNotActivatedException(login);
        }

        final List<GrantedAuthority> grantedAuthorities = user
            .getAuthorities()
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getId()))
            .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
