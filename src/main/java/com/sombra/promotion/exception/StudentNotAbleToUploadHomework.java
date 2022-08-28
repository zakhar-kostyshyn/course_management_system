package com.sombra.promotion.exception;

import java.util.UUID;

public class StudentNotAbleToUploadHomework extends RuntimeException {

    public StudentNotAbleToUploadHomework(UUID studentId, UUID courseId, UUID lessonId, Throwable e) {
        super("Student with ID: "+studentId.toString()+
                " cannot upload homework in course with ID: "+courseId.toString()+
                " and lesson with ID: "+lessonId, e);
    }

}
