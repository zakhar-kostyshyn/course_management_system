package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.UploadHomeworkRequest;
import com.sombra.promotion.dto.details.HomeworkDetails;
import com.sombra.promotion.factory.HomeworkDetailsFactory;
import com.sombra.promotion.repository.HomeworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final HomeworkDetailsFactory homeworkDetailsFactory;

    public HomeworkDetails saveHomework(UploadHomeworkRequest request) {
        UUID homeworkId = homeworkRepository.insertHomework(request);
        return homeworkDetailsFactory.build(homeworkId);
    }

}
