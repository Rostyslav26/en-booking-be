package com.websitre.enbookingbe.core.user.management.mapper;

import com.websitre.enbookingbe.core.user.management.domain.User;
import com.websitre.enbookingbe.core.user.management.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.websitre.enbookingbe.core.security.Authorities.ROLE_USER;
import static com.websitre.enbookingbe.core.security.SecurityUtils.*;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User toNewUser(UserRegisterRequest dto) {
        final User user = new User();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setImageUrl(dto.getImageUrl());
        user.setLangKey(dto.getLangKey());
        user.setAuthorities(Collections.singleton(toAuthority(ROLE_USER)));
        user.activated(false);
        user.setCreatedBy(DEFAULT_AUDITING);
        user.setLastModifiedBy(DEFAULT_AUDITING);
        user.setActivationKey(generateActivationKey());

        return user;
    }
}
