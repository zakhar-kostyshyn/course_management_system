package com.sombra.promotion.factory;

import com.sombra.promotion.dto.response.MarkResponse;
import com.sombra.promotion.repository.MarkRepository;
import com.sombra.promotion.tables.pojos.Mark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MarkFactory {

    private final MarkRepository markRepository;
    private final UserFactory userFactory;
    private final LessonFactory lessonFactory;

    public MarkResponse build(UUID markId) {
        Mark mark = markRepository.selectMarkBy(markId);
        return MarkResponse.builder()
                .markId(mark.getId())
                .markValue(mark.getMark())
                .estimatedLesson(lessonFactory.build(mark.getLessonId()))
                .instructorWhoPut(userFactory.build(mark.getInstructorId()))
                .studentWhoReceive(userFactory.build(mark.getStudentId()))
                .build();
    }

    public List<MarkResponse> build(List<Mark> marks) {
        return marks.stream().map(mark ->
                MarkResponse.builder()
                        .markId(mark.getId())
                        .markValue(mark.getMark())
                        .estimatedLesson(lessonFactory.build(mark.getLessonId()))
                        .studentWhoReceive(userFactory.build(mark.getStudentId()))
                        .instructorWhoPut(userFactory.build(mark.getInstructorId()))
                        .build()
        ).collect(Collectors.toList());
    }

}

