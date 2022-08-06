package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public class MarkDetails {

    @NonNull private UUID markId;
    private int markValue;
    @NonNull UserDetails instructorWhoPut;
    @NonNull UserDetails studentWhoReceive;
    @NonNull LessonDetails estimatedLesson;

}
