package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.MarkDetails;
import com.sombra.promotion.repository.MarkRepository;
import com.sombra.promotion.tables.pojos.Mark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
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

    public List<MarkDetails> build(List<Mark> marks) {
        return marks.stream().map(mark ->
                MarkDetails.builder()
                        .markId(mark.getId())
                        .markValue(mark.getMark())
                        .estimatedLesson(lessonDetailsFactory.build(mark.getLessonId()))
                        .studentWhoReceive(userDetailsFactory.build(mark.getStudentId()))
                        .instructorWhoPut(userDetailsFactory.build(mark.getInstructorId()))
                        .build()
        ).collect(Collectors.toList());
    }

}

