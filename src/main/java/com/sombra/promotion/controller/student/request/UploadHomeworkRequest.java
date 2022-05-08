package com.sombra.promotion.controller.student.request;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadHomeworkRequest {

    @NonNull private final MultipartFile homework;
    @NonNull private final String student;

}
