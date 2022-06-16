package com.sombra.promotion.controller.student.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class StudentCourseLessonResponse {

    @NonNull private final String lesson;
    private final Integer mark;

}
