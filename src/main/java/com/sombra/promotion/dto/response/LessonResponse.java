package com.sombra.promotion.dto.response;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {

    @NonNull private UUID lessonId;
    @NonNull private String lessonName;
    @NonNull private CourseResponse course;

}
