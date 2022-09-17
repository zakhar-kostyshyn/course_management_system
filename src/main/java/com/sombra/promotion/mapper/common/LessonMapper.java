package com.sombra.promotion.mapper.common;

import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.abstraction.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonMapper extends AbstractResponseFactory<Lesson, LessonResponse, LessonRepository> {

    private final LessonRepository lessonRepository;
    private final CourseMapper courseMapper;

    @Override
    public LessonRepository getRepository() {
        return lessonRepository;
    }

    @Override
    public LessonResponse build(Lesson lesson) {
        return LessonResponse.builder()
                .lessonId(lesson.getId())
                .lessonName(lesson.getName())
                .course(courseMapper.build(lesson.getCourseId()))
                .build();
    }

}
