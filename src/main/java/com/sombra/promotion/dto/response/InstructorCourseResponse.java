package com.sombra.promotion.dto.response;

import lombok.*;
import org.springframework.lang.NonNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorCourseResponse {

    @NonNull private UserResponse user;
    @NonNull private CourseResponse course;

}
