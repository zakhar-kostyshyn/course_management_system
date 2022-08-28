package com.sombra.promotion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LessonDontHaveStudentOrInstructorException extends RuntimeException {

    public LessonDontHaveStudentOrInstructorException(UUID studentId, UUID instructorId, UUID lessonId, Throwable e) {
        super("Instructor with ID: "+instructorId+
                " cannot create feedback for student with ID: "+studentId+
                " in lesson with ID: "+lessonId, e);
    }

}
