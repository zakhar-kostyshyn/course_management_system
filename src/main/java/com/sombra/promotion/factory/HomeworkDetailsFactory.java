package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.HomeworkDetails;
import com.sombra.promotion.repository.HomeworkRepository;
import com.sombra.promotion.tables.pojos.Homework;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HomeworkDetailsFactory {

    private final HomeworkRepository homeworkRepository;
    private final LessonDetailsFactory lessonDetailsFactory;
    private final UserDetailsFactory userDetailsFactory;

    public HomeworkDetails build(UUID homeworkId) {
        Homework homework = homeworkRepository.selectHomeworkBy(homeworkId);
        return HomeworkDetails.builder()
                .homeworkId(homework.getId())
                .lesson(lessonDetailsFactory.build(homework.getLessonId()))
                .student(userDetailsFactory.build(homework.getStudentId()))
                .build();
    }

}
