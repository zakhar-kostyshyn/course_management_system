package com.sombra.promotion.dto.response;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkResponse {

    @NonNull private UUID homeworkId;
    @NonNull private UserResponse user;
    @NonNull private LessonResponse lesson;

}
