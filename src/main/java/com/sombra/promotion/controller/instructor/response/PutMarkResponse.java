package com.sombra.promotion.controller.instructor.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class PutMarkResponse {

    @NonNull private final Integer lessonId;
    @NonNull private final Integer mark;

}
