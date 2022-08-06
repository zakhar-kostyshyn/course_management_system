package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class StudentCourseDetails {

    @NonNull private UserDetails userDetails;
    @NonNull private CourseDetails courseDetails;

}
