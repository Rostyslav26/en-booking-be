package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.model.RegistrationRequest;
import com.website.enbookingbe.core.user.management.domain.User;

public interface UserMapper {
    User toNewUser(RegistrationRequest dto);
}
