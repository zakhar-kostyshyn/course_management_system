package com.sombra.promotion.dto.request;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class UploadHomeworkRequest {

    @NonNull private final MultipartFile homework;
    @NonNull private final String student;
    @NotNull private final String lesson;
    @NotNull private final String course;

}
