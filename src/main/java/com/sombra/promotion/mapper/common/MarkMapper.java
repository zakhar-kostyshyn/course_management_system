package com.sombra.promotion.mapper.common;

import com.sombra.promotion.dto.response.MarkResponse;
import com.sombra.promotion.abstraction.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.MarkRepository;
import com.sombra.promotion.tables.pojos.Mark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarkMapper extends AbstractResponseFactory<Mark, MarkResponse, MarkRepository> {

    private final MarkRepository markRepository;
    private final UserMapper userMapper;
    private final LessonMapper lessonMapper;

    @Override
    public MarkRepository getRepository() {
        return markRepository;
    }

    @Override
    public MarkResponse build(Mark mark) {
        return MarkResponse.builder()
                .markId(mark.getId())
                .markValue(mark.getMark())
                .estimatedLesson(lessonMapper.build(mark.getLessonId()))
                .instructorWhoPut(userMapper.build(mark.getInstructorId()))
                .studentWhoReceive(userMapper.build(mark.getStudentId()))
                .build();
    }

}

