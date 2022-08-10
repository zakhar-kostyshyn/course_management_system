package com.sombra.promotion.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Data
public class FinishCourseRequest {

    @NonNull private String studentUsername;
    @NonNull private String courseName;

}
