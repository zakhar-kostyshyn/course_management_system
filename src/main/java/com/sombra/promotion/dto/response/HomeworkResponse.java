package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public class HomeworkResponse {

    @NonNull private UUID homeworkId;
    @NonNull private UserResponse student;
    @NonNull private LessonResponse lesson;

}
