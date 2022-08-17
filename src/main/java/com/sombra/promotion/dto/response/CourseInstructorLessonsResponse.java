package com.sombra.promotion.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseInstructorLessonsResponse {

    @NonNull private List<InstructorCourseResponse> instructorsCourses;
    @NonNull private CourseResponse course;
    @NonNull private List<LessonResponse> lessons;

}
