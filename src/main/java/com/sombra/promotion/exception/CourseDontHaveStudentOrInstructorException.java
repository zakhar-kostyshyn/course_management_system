package com.sombra.promotion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CourseDontHaveStudentOrInstructorException extends RuntimeException {

    public CourseDontHaveStudentOrInstructorException(UUID studentId, UUID instructorId, UUID courseId, Throwable e) {
        super("Instructor with ID: "+instructorId+
                " cannot create feedback for student with ID: "+studentId+
                " in course with ID: "+courseId, e);
    }

}
