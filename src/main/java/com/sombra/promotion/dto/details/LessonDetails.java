package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public class LessonDetails {

    @NonNull private UUID lessonId;
    @NonNull private String lessonName;
    @NonNull private CourseDetails course;

}
