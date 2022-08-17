package com.sombra.promotion.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseRequest {

    @NonNull private List<UUID> instructorIds;
    @NonNull private String courseName;
    @NonNull private List<String> lessons;

}
