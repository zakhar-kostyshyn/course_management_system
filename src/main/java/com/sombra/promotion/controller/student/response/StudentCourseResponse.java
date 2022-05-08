package com.sombra.promotion.controller.student.response;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class StudentCourseResponse {

    @NonNull private final String course;
    @NonNull private final String student;
    @NonNull private final List<StudentCourseLessonResponse> lessons;

}
