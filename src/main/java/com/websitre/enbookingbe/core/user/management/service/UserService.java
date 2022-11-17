package com.websitre.enbookingbe.core.user.management.service;

import com.websitre.enbookingbe.core.exception.NotFoundException;
import com.websitre.enbookingbe.core.user.management.domain.User;
import com.websitre.enbookingbe.core.user.management.exception.UserAlreadyExistsException;
import com.websitre.enbookingbe.core.user.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        final String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        return userRepository.save(user);
    }

    public void activate(String activationKey) {
        final User user = userRepository.findByActivationKey(activationKey)
            .orElseThrow(() -> new NotFoundException("User not found by activation key. Key: %s", activationKey));

        user.activated(true);
        user.setActivationKey(null);

        log.debug("User '{}' has been activated", user.getEmail());
    }
}
