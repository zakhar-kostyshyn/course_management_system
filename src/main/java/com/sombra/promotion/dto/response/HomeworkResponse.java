package com.sombra.promotion.dto.response;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkResponse {

    @NonNull private UUID homeworkId;
    @NonNull private UserResponse student;
    @NonNull private LessonResponse lesson;

}
