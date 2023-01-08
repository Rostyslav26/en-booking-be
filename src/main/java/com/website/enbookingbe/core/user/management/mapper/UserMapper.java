package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.core.user.management.model.RegistrationRequest;
import com.website.enbookingbe.core.user.management.model.UserProfile;

public interface UserMapper {
    User toNewUser(RegistrationRequest dto);
    UserProfile toUserProfile(User user);
}
