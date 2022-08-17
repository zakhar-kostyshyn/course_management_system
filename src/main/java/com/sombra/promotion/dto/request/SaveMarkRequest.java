package com.sombra.promotion.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveMarkRequest {

    private int mark;
    @NonNull private UUID studentId;
    @NonNull private UUID instructorId;
    @NonNull private UUID lessonId;
    @NonNull private UUID courseId;

}