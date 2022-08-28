package com.sombra.promotion.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

import static com.sombra.promotion.service.util.statics.SecurityPrincipalUtil.authenticatedUserId;

@Data
@NoArgsConstructor
@Getter
public class FinishCourseRequest {

    @NonNull private UUID courseId;

    @Nullable @JsonIgnore
    private UUID studentId;

    public FinishCourseRequest(@NonNull UUID courseId) {
        this.courseId = courseId;
    }

}
