package com.sombra.promotion.controller.instructor.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class GiveFinalFeedbackRequest {

    @NonNull private final String feedback;
    @NonNull private final String course;
    @NonNull private final String student;

}
