package com.sombra.promotion.dto.response;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseLessonsResponse {

    @NonNull private UserResponse user;
    @NonNull private CourseResponse course;
    @NonNull private List<LessonResponse> lessons;

}
