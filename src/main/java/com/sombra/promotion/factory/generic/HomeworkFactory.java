package com.sombra.promotion.factory.generic;

import com.sombra.promotion.dto.response.HomeworkResponse;
import com.sombra.promotion.interfaces.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.HomeworkRepository;
import com.sombra.promotion.tables.pojos.Homework;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeworkFactory extends AbstractResponseFactory<Homework, HomeworkResponse, HomeworkRepository> {

    private final HomeworkRepository homeworkRepository;
    private final LessonFactory lessonFactory;
    private final UserFactory userFactory;

    @Override
    public HomeworkRepository getDao() {
        return homeworkRepository;
    }

    @Override
    public HomeworkResponse build(Homework homework) {
        return HomeworkResponse.builder()
                .homeworkId(homework.getId())
                .lesson(lessonFactory.build(homework.getLessonId()))
                .student(userFactory.build(homework.getStudentId()))
                .build();
    }

}
