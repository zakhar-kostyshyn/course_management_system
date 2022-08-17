package com.sombra.promotion.exception;

import java.util.UUID;

import static java.lang.String.format;

public class NotFoundCourseBelongsForInstructorException extends RuntimeException {

    private final static String ERROR_MESSAGE = "Course with ID: %s and instructor with ID: %s";

    public NotFoundCourseBelongsForInstructorException(UUID courseId, UUID instructorId) {
        super(format(ERROR_MESSAGE, courseId, instructorId));
    }

}
