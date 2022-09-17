package com.sombra.promotion.mapper.common;

import com.sombra.promotion.dto.response.HomeworkResponse;
import com.sombra.promotion.abstraction.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.HomeworkRepository;
import com.sombra.promotion.tables.pojos.Homework;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeworkMapper extends AbstractResponseFactory<Homework, HomeworkResponse, HomeworkRepository> {

    private final HomeworkRepository homeworkRepository;
    private final LessonMapper lessonMapper;
    private final UserMapper userMapper;

    @Override
    public HomeworkRepository getRepository() {
        return homeworkRepository;
    }

    @Override
    public HomeworkResponse build(Homework homework) {
        return HomeworkResponse.builder()
                .homeworkId(homework.getId())
                .lesson(lessonMapper.build(homework.getLessonId()))
                .user(userMapper.build(homework.getStudentId()))
                .build();
    }

}
