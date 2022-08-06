package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class LessonsOfCourseAndStudentDetails {

    @NonNull private List<LessonDetails> lessons;
    @NonNull private UserDetails student;
    @NonNull private CourseDetails course;

}
