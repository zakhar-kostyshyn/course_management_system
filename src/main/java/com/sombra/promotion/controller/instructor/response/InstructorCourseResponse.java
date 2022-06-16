package com.sombra.promotion.controller.instructor.response;

import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Data
public class InstructorCourseResponse {

    @NonNull private final UUID id;
    @NonNull private final List<InstructorCourseStudentResponse> students;
    @NonNull private final String courseName;

}
