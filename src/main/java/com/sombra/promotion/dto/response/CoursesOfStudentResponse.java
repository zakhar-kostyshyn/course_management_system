package com.sombra.promotion.dto.response;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoursesOfStudentResponse {

    @NonNull private List<CourseResponse> courses;
    @NonNull private UserResponse user;

}
