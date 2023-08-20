package com.website.enbookingbe.management;

import com.website.enbookingbe.exception.NotFoundException;
import com.website.enbookingbe.management.domain.User;
import com.website.enbookingbe.management.exception.UserAlreadyExistsException;
import com.website.enbookingbe.management.repository.RoleRepository;
import com.website.enbookingbe.management.repository.UserRepository;
import com.website.enbookingbe.management.resource.RegistrationResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.website.enbookingbe.data.jooq.Tables.USERS;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public User save(RegistrationResource resource) {
        final String email = resource.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        final User user = userRepository.save(userMapper.toNewUser(resource));
        roleRepository.save(user.getId(), user.getRoles());

        return user;
    }

    public void activate(String activationKey) {
        final User user = userRepository.findByActivationKey(activationKey)
            .orElseThrow(() -> new NotFoundException("User not found by activation key. Key: %s", activationKey));

        user.setActivated(true);
        user.setActivationKey(null);

        userRepository.update(user, USERS.ACTIVATED, USERS.ACTIVATION_KEY);

        log.debug("User '{}' has been activated", user.getEmail());
    }
}
