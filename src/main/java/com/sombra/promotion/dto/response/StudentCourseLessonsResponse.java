package com.sombra.promotion.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseLessonsResponse {

    @NonNull private UserResponse student;
    @NonNull private CourseResponse course;
    @NonNull private List<LessonResponse> lessons;

}
