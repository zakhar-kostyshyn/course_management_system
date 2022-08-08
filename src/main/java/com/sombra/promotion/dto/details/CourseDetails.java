package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public class CourseDetails {

    @NonNull private UUID id;
    @NonNull private String courseName;

}

