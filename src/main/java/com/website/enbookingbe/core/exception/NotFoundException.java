package com.website.enbookingbe.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4933673762286702956L;

    public NotFoundException(String message, Object... objects) {
        super(String.format(message, objects));
    }
}
