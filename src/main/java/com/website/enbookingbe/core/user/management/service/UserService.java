package com.website.enbookingbe.core.user.management.service;

import com.website.enbookingbe.core.exception.NotFoundException;
import com.website.enbookingbe.core.user.management.entity.User;
import com.website.enbookingbe.core.user.management.exception.UserAlreadyExistsException;
import com.website.enbookingbe.core.user.management.exception.UserNotFoundException;
import com.website.enbookingbe.core.user.management.resource.UserInfo;
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

    public User save(User user) {
        final String email = user.getEmail();
        
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        userRepository.save(user);

        return user;
    }

    public void activate(String activationKey) {
        final User user = userRepository.findByActivationKey(activationKey)
            .orElseThrow(() -> new NotFoundException("User not found by activation key. Key: %s", activationKey));

        user.setActivated(true);
        user.setActivationKey(null);

        log.debug("User '{}' has been activated", user.getEmail());
    }

    public UserInfo getUserInfo(Integer id) {
        return userRepository.getUserInfoById(id)
            .orElseThrow(UserNotFoundException::new);
    }
}
