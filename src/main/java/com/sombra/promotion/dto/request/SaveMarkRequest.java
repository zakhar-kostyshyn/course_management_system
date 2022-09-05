package com.sombra.promotion.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveMarkRequest {

    private int mark;
    @NonNull private UUID studentId;
    @NonNull private UUID lessonId;

    @Nullable @JsonIgnore
    private UUID instructorId;

    public SaveMarkRequest(int mark, @NonNull UUID studentId, @NonNull UUID lessonId) {
        this.mark = mark;
        this.studentId = studentId;
        this.lessonId = lessonId;
    }

}
