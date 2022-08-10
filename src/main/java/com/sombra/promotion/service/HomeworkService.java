package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.UploadHomeworkRequest;
import com.sombra.promotion.dto.response.HomeworkResponse;
import com.sombra.promotion.factory.HomeworkFactory;
import com.sombra.promotion.repository.HomeworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final HomeworkFactory homeworkFactory;

    public HomeworkResponse saveHomework(UploadHomeworkRequest request) {
        UUID homeworkId = homeworkRepository.insertHomework(request);
        return homeworkFactory.build(homeworkId);
    }

}
