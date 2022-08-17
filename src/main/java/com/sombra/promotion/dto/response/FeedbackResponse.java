package com.sombra.promotion.dto.response;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

    @NonNull private UUID feedbackId;
    @NonNull private String feedback;
    @NonNull private UserResponse studentWhoReceive;
    @NonNull private UserResponse instructorWhoLeft;
    @NonNull private CourseResponse courseResponse;

}
