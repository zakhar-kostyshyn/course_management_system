package com.sombra.promotion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughLessonsForCourseException  extends RuntimeException {

    public NotEnoughLessonsForCourseException(int actualNumberOfLessons, int requiredNumberOfLessons) {
        super("Can not create course with "+actualNumberOfLessons+" number of lessons. Required is "+requiredNumberOfLessons+".");
    }

}
