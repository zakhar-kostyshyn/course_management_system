package com.sombra.promotion.dto.response;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorCourseStudentsResponse {

    @NonNull private List<UserResponse> students;
    @NonNull private CourseResponse course;
    @NonNull private UserResponse instructor;

}
