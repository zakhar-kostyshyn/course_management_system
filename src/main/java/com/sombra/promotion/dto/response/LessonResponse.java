package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Builder
@Getter
public class LessonResponse {

    @NonNull private UUID lessonId;
    @NonNull private String lessonName;
    @NonNull private CourseResponse course;

}
