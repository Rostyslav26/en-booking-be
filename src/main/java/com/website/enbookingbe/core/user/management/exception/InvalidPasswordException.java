package com.website.enbookingbe.core.user.management.exception;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;

import java.io.Serial;

@ResponseErrorCode("INVALID_PASSWORD")
public class InvalidPasswordException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8968395764265032790L;

    public InvalidPasswordException(String message) {
        super(message);
    }
}
