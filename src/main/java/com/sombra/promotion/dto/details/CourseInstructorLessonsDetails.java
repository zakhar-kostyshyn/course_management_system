package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class CourseInstructorLessonsDetails {

    @NonNull private List<InstructorCourseDetails> instructorCourseDetails;
    @NonNull private CourseDetails courseDetails;
    @NonNull private List<LessonDetails> lessonDetails;

}
