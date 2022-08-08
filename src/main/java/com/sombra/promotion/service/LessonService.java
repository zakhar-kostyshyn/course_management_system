package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.CreateLessonsRequest;
import com.sombra.promotion.dto.details.LessonDetails;
import com.sombra.promotion.factory.LessonDetailsFactory;
import com.sombra.promotion.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonDetailsFactory lessonDetailsFactory;

    public List<LessonDetails> createLesson(CreateLessonsRequest request) {
        List<UUID> ids = lessonRepository.insertLesson(request.getLessons(), request.getCourse());
        return lessonDetailsFactory.build(ids);
    }

}
