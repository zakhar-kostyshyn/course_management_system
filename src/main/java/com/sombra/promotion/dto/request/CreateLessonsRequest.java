package com.sombra.promotion.dto.request;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLessonsRequest {

    @NonNull private UUID courseId;
    @NonNull private List<String> lessons;

}
