package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class InstructorCourseDetails {

    @NonNull private UserDetails userDetails;
    @NonNull private CourseDetails courseDetails;

}
