package com.sombra.promotion.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class AssignInstructorForCourseRequest {

    @NonNull private final String instructor;
    @NonNull private final String course;

}
