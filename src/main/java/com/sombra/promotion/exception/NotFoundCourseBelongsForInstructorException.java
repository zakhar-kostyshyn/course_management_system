package com.sombra.promotion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundCourseBelongsForInstructorException extends RuntimeException {

    private final static String ERROR_MESSAGE = "Course with ID: %s and instructor with ID: %s";

    public NotFoundCourseBelongsForInstructorException(UUID courseId, UUID instructorId) {
        super(format(ERROR_MESSAGE, courseId, instructorId));
    }

}
