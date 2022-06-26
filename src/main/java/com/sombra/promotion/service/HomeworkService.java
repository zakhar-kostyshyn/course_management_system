package com.sombra.promotion.service;

import com.sombra.promotion.controller.student.request.UploadHomeworkRequest;
import com.sombra.promotion.repository.HomeworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final HomeworkRepository homeworkRepository;

    public void saveHomework(UploadHomeworkRequest request) {
        homeworkRepository.insertHomework(request);
    }

}
