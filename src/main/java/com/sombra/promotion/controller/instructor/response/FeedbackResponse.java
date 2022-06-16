package com.sombra.promotion.controller.instructor.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class FeedbackResponse {

    @NonNull private final Integer id;
    @NonNull private final String feedback;
    @NonNull private final String course;
    @NonNull private final String student;
    @NonNull private final String instructor;

}
