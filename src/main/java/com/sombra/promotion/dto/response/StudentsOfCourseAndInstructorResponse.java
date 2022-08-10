package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class StudentsOfCourseAndInstructorResponse {

    @NonNull private List<UserResponse> students;
    @NonNull private CourseResponse courseResponse;
    @NonNull private UserResponse instructor;

}
