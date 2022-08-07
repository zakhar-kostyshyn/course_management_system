package com.sombra.promotion.exception;

import java.util.UUID;

import static java.lang.String.format;

public class NotFoundCourseBelongsForStudentException extends RuntimeException{

    private final static String ERROR_MESSAGE = "Course with ID: %s and student with username: %s";

    public NotFoundCourseBelongsForStudentException(UUID courseId, String student) {
        super(format(ERROR_MESSAGE, courseId, student));
    }

}
