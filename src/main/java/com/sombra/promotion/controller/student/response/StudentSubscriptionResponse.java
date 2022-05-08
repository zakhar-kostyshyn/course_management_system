package com.sombra.promotion.controller.student.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class StudentSubscriptionResponse {

    @NonNull private final Integer id;
    @NonNull private final String student;
    @NonNull private final String courseSubscribed;

}
