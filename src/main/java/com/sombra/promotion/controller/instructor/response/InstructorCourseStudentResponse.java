package com.sombra.promotion.controller.instructor.response;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class InstructorCourseStudentResponse {

    @NonNull private final UUID id;
    @NonNull private final String username;

}
