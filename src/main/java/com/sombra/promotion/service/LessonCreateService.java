package com.sombra.promotion.service;

import com.sombra.promotion.controller.instructor.request.CreateLessonRequest;
import com.sombra.promotion.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonCreateService {

    private final LessonRepository lessonRepository;

    public void createLesson(CreateLessonRequest request) {
        lessonRepository.insertLesson(request.getLesson(), request.getCourse());
    }

}
