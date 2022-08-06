package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.CreateLessonRequest;
import com.sombra.promotion.dto.details.LessonDetails;
import com.sombra.promotion.factory.LessonDetailsFactory;
import com.sombra.promotion.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonDetailsFactory lessonDetailsFactory;

    public LessonDetails createLesson(CreateLessonRequest request) {
        UUID id = lessonRepository.insertLesson(request.getLesson(), request.getCourse());
        return lessonDetailsFactory.build(id);
    }

}
