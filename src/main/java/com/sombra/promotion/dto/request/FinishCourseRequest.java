package com.sombra.promotion.dto.request;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FinishCourseRequest {

    @NonNull private UUID studentId;
    @NonNull private UUID courseId;

}
