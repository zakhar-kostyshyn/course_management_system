package com.sombra.promotion.exception.global;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundException extends RuntimeException {

    public NotFoundException(UUID id, String tableName) {
        super("Not found row with ID: "+id+" in table "+tableName+".");
    }

    public NotFoundException(String tableName) {
        super("Not found row by condition in table: "+tableName+".");
    }
}
