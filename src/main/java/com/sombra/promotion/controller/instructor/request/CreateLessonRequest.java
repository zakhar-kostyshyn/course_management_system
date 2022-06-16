package com.sombra.promotion.controller.instructor.request;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateLessonRequest {

    @NonNull private final String instructor;
    @NonNull private final String course;
    @NonNull private final String lesson;

}
