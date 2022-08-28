package com.sombra.promotion.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

import static com.sombra.promotion.service.util.statics.SecurityPrincipalUtil.authenticatedUserId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiveFinalFeedbackRequest {

    @NonNull private String feedback;
    @NonNull private UUID courseId;
    @NonNull private UUID studentId;

    @Nullable @JsonIgnore
    private UUID instructorId;

    public GiveFinalFeedbackRequest(@NonNull String feedback, @NonNull UUID courseId, @NonNull UUID studentId) {
        this.feedback = feedback;
        this.courseId = courseId;
        this.studentId = studentId;
    }

}
