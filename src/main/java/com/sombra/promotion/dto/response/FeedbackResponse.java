package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public class FeedbackResponse {

    @NonNull private UUID feedbackId;
    @NonNull private String feedback;
    @NonNull private UserResponse studentWhoReceive;
    @NonNull private UserResponse instructorWhoLeft;
    @NonNull private CourseResponse courseResponse;

}
