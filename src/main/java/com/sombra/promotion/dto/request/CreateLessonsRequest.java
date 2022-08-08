package com.sombra.promotion.dto.request;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CreateLessonsRequest {

    @NonNull private final String course;
    @NonNull private final List<String> lessons;

}
