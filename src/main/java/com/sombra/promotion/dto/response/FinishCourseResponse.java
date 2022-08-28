package com.sombra.promotion.dto.response;


import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FinishCourseResponse {

    @NonNull CourseMarkResponse courseMark;
    @NonNull List<MarkResponse> marks;

}
