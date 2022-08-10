package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public class FeedbackDetails {

    @NonNull private UUID feedbackId;
    @NonNull private String feedback;
    @NonNull private UserDetails studentWhoReceive;
    @NonNull private UserDetails instructorWhoLeft;
    @NonNull private CourseDetails courseDetails;

}
