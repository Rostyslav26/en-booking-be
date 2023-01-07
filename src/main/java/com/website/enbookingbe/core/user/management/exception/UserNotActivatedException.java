package com.website.enbookingbe.core.user.management.exception;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseErrorCode("USER_NOT_ACTIVATED")
public class UserNotActivatedException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 6199179084262398884L;

    public UserNotActivatedException(String username) {
        super(String.format("User %s was not activated", username));
    }
}
