package com.sombra.promotion.dto.response;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseResponse {

    @NonNull private UserResponse userResponse;
    @NonNull private CourseResponse courseResponse;

}
