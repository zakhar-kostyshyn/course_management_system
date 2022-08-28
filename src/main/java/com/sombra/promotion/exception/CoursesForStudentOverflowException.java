package com.sombra.promotion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CoursesForStudentOverflowException extends RuntimeException {

    public CoursesForStudentOverflowException(int maxCourseForStudent) {
        super("Student cannot take more then " + maxCourseForStudent + " courses");
    }

}
