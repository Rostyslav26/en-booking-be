package com.website.enbookingbe.core.user.management.exception;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;

import java.io.Serial;

@ResponseErrorCode("USER_ALREADY_EXISTS")
public class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5832043203001950616L;
    private final String reason;

    public UserAlreadyExistsException(String login) {
        super(String.format("User by %s already exists", login));
        this.reason = login;
    }

    public String getReason() {
        return reason;
    }
}
