package com.sombra.promotion.controller.instructor.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class InstructorCourseStudentResponse {

    @NonNull private final Integer id;
    @NonNull private final String name;
    @NonNull private final String role;

}
