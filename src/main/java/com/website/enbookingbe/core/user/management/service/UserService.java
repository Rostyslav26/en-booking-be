package com.website.enbookingbe.core.user.management.service;

import com.website.enbookingbe.core.exception.NotFoundException;
import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.core.user.management.exception.UserAlreadyExistsException;
import com.website.enbookingbe.core.user.management.exception.UserNotFoundException;
import com.website.enbookingbe.core.user.management.model.UserProfile;
import com.website.enbookingbe.core.user.management.repository.RoleRepository;
import com.website.enbookingbe.core.user.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User save(User user) {
        final String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        userRepository.save(user);
        roleRepository.assignRoles(user.getRoles(), user.getId());

        return user;
    }

    public void activate(String activationKey) {
        final User user = userRepository.findByActivationKey(activationKey)
            .orElseThrow(() -> new NotFoundException("User not found by activation key. Key: %s", activationKey));

        user.setActivated(true);
        user.setActivationKey(null);
        userRepository.update(user);

        log.debug("User '{}' has been activated", user.getEmail());
    }

    public UserProfile getProfile(String email) {
        return userRepository.findProfileByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(email));
    }
}
