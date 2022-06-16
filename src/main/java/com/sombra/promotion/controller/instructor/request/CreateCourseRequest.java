package com.sombra.promotion.controller.instructor.request;

import lombok.Data;
import lombok.NonNull;


@Data
public class CreateCourseRequest {

    @NonNull private final String instructor;
    @NonNull private final String course;

}
