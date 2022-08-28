package com.sombra.promotion.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignInstructorForCourseRequest {

    @NonNull private UUID instructorId;
    @NonNull private UUID courseId;

}
