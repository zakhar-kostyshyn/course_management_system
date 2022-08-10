package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.dto.request.CreateLessonsRequest;
import com.sombra.promotion.factory.LessonFactory;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonFactory lessonFactory;

    public List<LessonResponse> createLesson(CreateLessonsRequest request) {
        List<UUID> ids = lessonRepository.insertLesson(request.getLessons(), request.getCourse());
        return lessonFactory.build(ids);
    }

    public List<LessonResponse> getLessonsByCourse(String course) {
        return lessonFactory.build(
                lessonRepository.selectLessonsByCourseName(course).stream().map(Lesson::getId).collect(toList())
        );
    }

}
