package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.entity.Role;
import com.website.enbookingbe.core.user.management.entity.User;
import com.website.enbookingbe.core.user.management.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static com.website.enbookingbe.core.security.SecurityUtils.DEFAULT_AUDITING;
import static com.website.enbookingbe.core.security.SecurityUtils.generateActivationKey;
import static com.website.enbookingbe.core.security.SpringRole.ROLE_USER;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private static final String DEFAULT_LANG_KEY = "en";

    private final PasswordEncoder passwordEncoder;

    @Override
    public User toNewUser(RegistrationRequest dto) {
        final User user = new User();

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        user.setLangKey(DEFAULT_LANG_KEY);

        user.addRole(new Role(ROLE_USER.name()));
        user.setActivated(false);
        user.setCreatedBy(DEFAULT_AUDITING);
        user.setCreatedDate(Instant.now());
        user.setLastModifiedBy(DEFAULT_AUDITING);
        user.setLastModifiedDate(Instant.now());
        user.setActivationKey(generateActivationKey());

        return user;
    }
}
