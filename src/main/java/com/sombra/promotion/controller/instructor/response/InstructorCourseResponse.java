package com.sombra.promotion.controller.instructor.response;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class InstructorCourseResponse {

    @NonNull private final Integer id;
    @NonNull private final List<InstructorCourseStudentResponse> students;
    @NonNull private final String name;

}
