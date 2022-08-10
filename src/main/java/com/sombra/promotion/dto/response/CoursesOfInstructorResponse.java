package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class CoursesOfInstructorResponse {

    @NonNull private List<CourseResponse> courses;
    @NonNull private UserResponse username;

}
