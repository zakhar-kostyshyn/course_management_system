package com.sombra.promotion.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiveFinalFeedbackRequest {

    @NonNull private String feedback;
    @NonNull private UUID courseId;
    @NonNull private UUID studentId;
    @NonNull private UUID instructorId;

}
