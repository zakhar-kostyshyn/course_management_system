package com.sombra.promotion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseMarkResponse {

    @NonNull private UUID id;
    @NonNull private UserResponse student;
    @NonNull private CourseResponse course;
    private boolean isCoursePassed;
    private double courseMark;

}
