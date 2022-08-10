package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class LessonsOfCourseAndStudentResponse {

    @NonNull private List<LessonResponse> lessons;
    @NonNull private UserResponse student;
    @NonNull private CourseResponse course;

}
