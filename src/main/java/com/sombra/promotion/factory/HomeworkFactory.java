package com.sombra.promotion.factory;

import com.sombra.promotion.dto.response.HomeworkResponse;
import com.sombra.promotion.repository.HomeworkRepository;
import com.sombra.promotion.tables.pojos.Homework;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HomeworkFactory {

    private final HomeworkRepository homeworkRepository;
    private final LessonFactory lessonFactory;
    private final UserFactory userFactory;

    public HomeworkResponse build(UUID homeworkId) {
        Homework homework = homeworkRepository.selectHomeworkBy(homeworkId);
        return HomeworkResponse.builder()
                .homeworkId(homework.getId())
                .lesson(lessonFactory.build(homework.getLessonId()))
                .student(userFactory.build(homework.getStudentId()))
                .build();
    }

}
