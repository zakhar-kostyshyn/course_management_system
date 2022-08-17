package com.sombra.promotion.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoursesOfStudentResponse {

    @NonNull private List<CourseResponse> courses;
    @NonNull private UserResponse student;

}
