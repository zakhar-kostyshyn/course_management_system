package com.sombra.promotion.controller.instructor.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class PutMarkRequest {

    @NonNull private final Integer mark;
    @NonNull private final String student;
    @NonNull private final String lesson;
    @NonNull private final String course;

}
