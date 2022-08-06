package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.MarkDetails;
import com.sombra.promotion.repository.MarkRepository;
import com.sombra.promotion.tables.pojos.Mark;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Builder
@RequiredArgsConstructor
public class MarkDetailsFactory {

    private final MarkRepository markRepository;
    private final UserDetailsFactory userDetailsFactory;
    private final LessonDetailsFactory lessonDetailsFactory;

    public MarkDetails build(UUID markId) {
        Mark mark = markRepository.selectMarkBy(markId);
        return MarkDetails.builder()
                .markId(mark.getId())
                .markValue(mark.getMark())
                .estimatedLesson(lessonDetailsFactory.build(mark.getLessonId()))
                .instructorWhoPut(userDetailsFactory.build(mark.getInstructorId()))
                .studentWhoReceive(userDetailsFactory.build(mark.getStudentId()))
                .build();
    }

}

