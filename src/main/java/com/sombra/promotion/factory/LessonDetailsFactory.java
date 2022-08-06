package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.LessonDetails;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LessonDetailsFactory {

    private final LessonRepository lessonRepository;
    private final CourseDetailsFactory courseDetailsFactory;

    public LessonDetails build(UUID lessonId) {
        Lesson lesson = lessonRepository.selectLessonBy(lessonId);
        return LessonDetails.builder()
                .lessonId(lesson.getId())
                .lessonName(lesson.getName())
                .course(courseDetailsFactory.build(lesson.getCourseId()))
                .build();
    }

}
