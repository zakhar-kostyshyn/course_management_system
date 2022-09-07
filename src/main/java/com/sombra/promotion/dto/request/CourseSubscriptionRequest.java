package com.sombra.promotion.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSubscriptionRequest {

    @NonNull private UUID courseId;

    @Nullable @JsonIgnore
    private UUID studentId;

    public CourseSubscriptionRequest(@NonNull UUID courseId) {
        this.courseId = courseId;
    }

}
