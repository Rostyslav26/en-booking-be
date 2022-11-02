package com.websitre.enbookingbe.core.user.management.mapper;

import com.websitre.enbookingbe.core.user.management.domain.User;
import com.websitre.enbookingbe.core.user.management.dto.UserRegisterRequest;

public interface UserMapper {
    User toNewUser(UserRegisterRequest dto);
}
