package com.sombra.promotion.service;

import com.sombra.promotion.controller.student.request.UploadHomeworkRequest;
import com.sombra.promotion.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final DomainRepository repository;

    public void saveHomework(UploadHomeworkRequest request) {
        repository.insertHomework(request);
    }

}
