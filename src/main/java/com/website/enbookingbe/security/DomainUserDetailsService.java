package com.website.enbookingbe.security;

import com.website.enbookingbe.management.entity.Role;
import com.website.enbookingbe.management.entity.User;
import com.website.enbookingbe.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Principal loadUserByUsername(String login) {
        log.debug("Authenticating {}", login);

        final User user = userRepository.findByEmail(login)
            .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));

        return createPrincipal(user);
    }

    private Principal createPrincipal(User user) {
        return Principal.builder()
            .id(user.getId())
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities(getRoles(user.getRoles()))
            .build();
    }

    private List<SimpleGrantedAuthority> getRoles(Set<Role> roles) {
        return roles.stream()
            .map(Role::getId)
            .map(SimpleGrantedAuthority::new)
            .toList();
    }
}
