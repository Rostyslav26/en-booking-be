package com.websitre.enbookingbe.core.user.management.mapper;

import com.websitre.enbookingbe.core.user.management.domain.User;
import com.websitre.enbookingbe.core.user.management.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ap.internal.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.websitre.enbookingbe.core.security.Authorities.ROLE_USER;
import static com.websitre.enbookingbe.core.security.SecurityUtils.*;

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
        user.setImageUrl(dto.getImageUrl());
        String langKey = Strings.isEmpty(dto.getLangKey()) ? DEFAULT_LANG_KEY : dto.getLangKey();
        user.setLangKey(langKey);
        user.addAuthority((toAuthority(ROLE_USER)));
        user.activated(false);
        user.setCreatedBy(DEFAULT_AUDITING);
        user.setLastModifiedBy(DEFAULT_AUDITING);
        user.setActivationKey(generateActivationKey());

        return user;
    }
}
