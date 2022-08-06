package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class CoursesOfInstructorDetails {

    @NonNull private List<CourseDetails> courses;
    @NonNull private UserDetails username;

}
