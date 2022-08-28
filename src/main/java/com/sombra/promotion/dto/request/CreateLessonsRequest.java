package com.sombra.promotion.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLessonsRequest {

    @NonNull private UUID courseId;
    @NonNull private List<String> lessons;

    @Nullable @JsonIgnore
    private UUID instructorId;

    public CreateLessonsRequest(@NonNull UUID courseId, @NonNull List<String> lessons) {
        this.courseId = courseId;
        this.lessons = lessons;
    }

}
