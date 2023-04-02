package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.core.user.management.model.RegistrationRequest;

public interface UserMapper {
    User toNewUser(RegistrationRequest dto);
}
