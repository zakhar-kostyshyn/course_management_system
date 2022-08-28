package com.sombra.promotion.dto.response;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarkResponse {

    @NonNull private UUID markId;
    private int markValue;
    @NonNull UserResponse instructorWhoPut;
    @NonNull UserResponse studentWhoReceive;
    @NonNull LessonResponse estimatedLesson;

}
