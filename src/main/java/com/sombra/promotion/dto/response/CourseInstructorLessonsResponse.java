package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class CourseInstructorLessonsResponse {

    @NonNull private List<InstructorCourseResponse> instructorCourseDetails;
    @NonNull private CourseResponse courseResponse;
    @NonNull private List<LessonResponse> lessonDetails;

}
