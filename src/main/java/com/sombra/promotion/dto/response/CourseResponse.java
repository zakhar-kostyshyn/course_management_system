package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public class CourseResponse {

    @NonNull private UUID id;
    @NonNull private String courseName;

}

