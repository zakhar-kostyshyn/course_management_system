package com.sombra.promotion.dto.response;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    @NonNull private UUID id;
    @NonNull private String courseName;

}

