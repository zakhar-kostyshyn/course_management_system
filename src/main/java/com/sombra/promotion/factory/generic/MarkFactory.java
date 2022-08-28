package com.sombra.promotion.factory.generic;

import com.sombra.promotion.dto.response.MarkResponse;
import com.sombra.promotion.abstraction.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.MarkRepository;
import com.sombra.promotion.tables.pojos.Mark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarkFactory extends AbstractResponseFactory<Mark, MarkResponse, MarkRepository> {

    private final MarkRepository markRepository;
    private final UserFactory userFactory;
    private final LessonFactory lessonFactory;

    @Override
    public MarkRepository getRepository() {
        return markRepository;
    }

    @Override
    public MarkResponse build(Mark mark) {
        return MarkResponse.builder()
                .markId(mark.getId())
                .markValue(mark.getMark())
                .estimatedLesson(lessonFactory.build(mark.getLessonId()))
                .instructorWhoPut(userFactory.build(mark.getInstructorId()))
                .studentWhoReceive(userFactory.build(mark.getStudentId()))
                .build();
    }

}

