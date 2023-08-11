package com.website.enbookingbe.management;

import com.website.enbookingbe.management.domain.Role;
import com.website.enbookingbe.management.domain.User;
import com.website.enbookingbe.management.resource.RegistrationResource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.website.enbookingbe.security.SecurityUtils.generateActivationKey;
import static com.website.enbookingbe.security.SpringRole.ROLE_USER;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private static final String DEFAULT_LANG_KEY = "en";

    private final PasswordEncoder passwordEncoder;

    public User toNewUser(RegistrationResource dto) {
        final User user = new User();

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setLangKey(DEFAULT_LANG_KEY);
        user.addRole(new Role(ROLE_USER.name()));
        user.setActivated(false);
        user.setActivationKey(generateActivationKey());

        return user;
    }
}
