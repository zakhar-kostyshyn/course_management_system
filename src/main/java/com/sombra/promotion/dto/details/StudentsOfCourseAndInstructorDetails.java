package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class StudentsOfCourseAndInstructorDetails {

    @NonNull private List<UserDetails> students;
    @NonNull private CourseDetails courseDetails;
    @NonNull private UserDetails instructor;

}
