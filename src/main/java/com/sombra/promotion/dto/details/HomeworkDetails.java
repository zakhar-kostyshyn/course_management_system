package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public class HomeworkDetails {

    @NonNull private UUID homeworkId;
    @NonNull private UserDetails student;
    @NonNull private LessonDetails lesson;

}
