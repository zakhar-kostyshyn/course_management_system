package com.sombra.promotion.dto.request;

import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Set;


@Data
public class CreateCourseRequest {

    @NonNull private final List<String> instructors;
    @NonNull private final String course;
    @NonNull private final List<String> lessonNames;

}
