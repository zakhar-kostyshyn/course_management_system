package com.sombra.promotion.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseMarkResponse {

    @NonNull private UUID id;
    @NonNull private UserResponse student;
    @NonNull private CourseResponse course;
    @NonNull private List<MarkResponse> courseMarks;
    private boolean isCoursePassed;
    private double mark;

}
