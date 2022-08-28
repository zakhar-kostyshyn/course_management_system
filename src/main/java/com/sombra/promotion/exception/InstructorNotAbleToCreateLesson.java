package com.sombra.promotion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InstructorNotAbleToCreateLesson extends RuntimeException {

    public InstructorNotAbleToCreateLesson(UUID instructorId, UUID courseId, Throwable e) {
        super("Instructor with ID: "+instructorId.toString()+" cannot create lessons in course with ID: ("+courseId.toString()+")", e);
    }

}