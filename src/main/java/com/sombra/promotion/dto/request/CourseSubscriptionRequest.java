package com.sombra.promotion.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class CourseSubscriptionRequest {

    @NonNull private final String course;
    @NonNull private final String student;

}
