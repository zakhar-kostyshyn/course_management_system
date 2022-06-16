package com.sombra.promotion.service;

import com.sombra.promotion.controller.instructor.request.CreateLessonRequest;
import com.sombra.promotion.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonCreateService {

    private final DomainRepository repository;

    public void createLesson(CreateLessonRequest request) {
        repository.insertLesson(request.getLesson(), request.getCourse());
    }

}
