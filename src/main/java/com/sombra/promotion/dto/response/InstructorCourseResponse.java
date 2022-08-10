package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class InstructorCourseResponse {

    @NonNull private UserResponse userResponse;
    @NonNull private CourseResponse courseResponse;

}
