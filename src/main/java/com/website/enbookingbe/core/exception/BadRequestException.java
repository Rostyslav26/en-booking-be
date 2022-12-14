package com.website.enbookingbe.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6428001089060734743L;

    public BadRequestException(String message, Object... objects) {
        super(String.format(message, objects));
    }
}
