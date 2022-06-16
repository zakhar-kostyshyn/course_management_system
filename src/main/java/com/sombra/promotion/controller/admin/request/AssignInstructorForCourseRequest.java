package com.sombra.promotion.controller.admin.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class AssignInstructorForCourseRequest {

    @NonNull private final String instructor;
    @NonNull private final String course;

}
