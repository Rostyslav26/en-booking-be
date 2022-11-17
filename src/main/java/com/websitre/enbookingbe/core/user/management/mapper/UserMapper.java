package com.websitre.enbookingbe.core.user.management.mapper;

import com.websitre.enbookingbe.core.user.management.domain.User;
import com.websitre.enbookingbe.core.user.management.dto.RegistrationRequest;

public interface UserMapper {
    User toNewUser(RegistrationRequest dto);
}
