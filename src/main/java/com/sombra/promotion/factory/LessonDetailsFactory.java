package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.LessonDetails;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

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

    public List<LessonDetails> build(List<UUID> lessonIds) {
        return lessonIds.stream().map(this::build).collect(toList());
    }

}
