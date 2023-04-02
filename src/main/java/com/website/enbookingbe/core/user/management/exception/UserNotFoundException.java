package com.website.enbookingbe.core.user.management.exception;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;

import java.io.Serial;

@ResponseErrorCode("USER_NOT_FOUND")
public class UserNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6653046861856452666L;

    public UserNotFoundException(String login) {
        super("User " + login + " was not found");
    }

    public UserNotFoundException() {
        super("User was not found");
    }
}
