package com.website.enbookingbe.core.user.management.exception;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;

import java.io.Serial;

@ResponseErrorCode("USER_ALREADY_EXISTS")
public class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5832043203001950616L;
    private final String email;

    public UserAlreadyExistsException(String email) {
        super(String.format("User %s already exists", email));
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
