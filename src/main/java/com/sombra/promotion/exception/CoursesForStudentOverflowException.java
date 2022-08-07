package com.sombra.promotion.exception;

public class CoursesForStudentOverflowException extends RuntimeException {

    public CoursesForStudentOverflowException(int maxCourseForStudent) {
        super("Student cannot take more then " + maxCourseForStudent + " courses");
    }
}
