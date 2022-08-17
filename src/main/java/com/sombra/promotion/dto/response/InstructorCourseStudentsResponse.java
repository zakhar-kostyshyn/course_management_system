package com.sombra.promotion.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorCourseStudentsResponse {

    @NonNull private List<UserResponse> students;
    @NonNull private CourseResponse courseResponse;
    @NonNull private UserResponse instructor;

}
