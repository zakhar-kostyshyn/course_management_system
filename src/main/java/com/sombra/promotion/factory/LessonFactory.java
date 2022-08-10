package com.sombra.promotion.factory;

import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class LessonFactory {

    private final LessonRepository lessonRepository;
    private final CourseFactory courseFactory;

    public LessonResponse build(UUID lessonId) {
        Lesson lesson = lessonRepository.selectLessonBy(lessonId);
        return LessonResponse.builder()
                .lessonId(lesson.getId())
                .lessonName(lesson.getName())
                .course(courseFactory.build(lesson.getCourseId()))
                .build();
    }

    public List<LessonResponse> build(List<UUID> lessonIds) {
        return lessonIds.stream().map(this::build).collect(toList());
    }

}
