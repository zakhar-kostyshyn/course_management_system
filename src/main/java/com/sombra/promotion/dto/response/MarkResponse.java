package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Builder
@Getter
public class MarkResponse {

    @NonNull private UUID markId;
    private int markValue;
    @NonNull UserResponse instructorWhoPut;
    @NonNull UserResponse studentWhoReceive;
    @NonNull LessonResponse estimatedLesson;

}
