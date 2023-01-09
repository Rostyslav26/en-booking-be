package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.core.user.management.model.Person;
import com.website.enbookingbe.core.user.management.model.RegistrationRequest;
import com.website.enbookingbe.core.user.management.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.website.enbookingbe.core.security.SecurityUtils.DEFAULT_AUDITING;
import static com.website.enbookingbe.core.security.SecurityUtils.generateActivationKey;
import static com.website.enbookingbe.core.user.management.domain.Role.ROLE_USER;

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

        user.setRoles(Collections.singleton(ROLE_USER));
        user.setActivated(false);
        user.setCreatedBy(DEFAULT_AUDITING);
        user.setLastModifiedBy(DEFAULT_AUDITING);
        user.setActivationKey(generateActivationKey());

        return user;
    }
}
