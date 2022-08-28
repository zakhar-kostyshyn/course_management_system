package com.sombra.promotion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class IncorrectCredentialsException extends RuntimeException {

    public IncorrectCredentialsException(Throwable e) {
        super("Incorrect credentials", e);
    }

    public IncorrectCredentialsException() {
        super("Incorrect credentials");
    }

}
