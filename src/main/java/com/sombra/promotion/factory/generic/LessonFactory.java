package com.sombra.promotion.factory.generic;

import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.abstraction.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonFactory extends AbstractResponseFactory<Lesson, LessonResponse, LessonRepository> {

    private final LessonRepository lessonRepository;
    private final CourseFactory courseFactory;

    @Override
    public LessonRepository getRepository() {
        return lessonRepository;
    }

    @Override
    public LessonResponse build(Lesson lesson) {
        return LessonResponse.builder()
                .lessonId(lesson.getId())
                .lessonName(lesson.getName())
                .course(courseFactory.build(lesson.getCourseId()))
                .build();
    }

}
