package com.sombra.promotion.service.generic;

import com.sombra.promotion.util.UUIDUtil;
import com.sombra.promotion.dto.request.SaveMarkRequest;
import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.dto.response.MarkResponse;
import com.sombra.promotion.factory.generic.MarkFactory;
import com.sombra.promotion.repository.MarkRepository;
import com.sombra.promotion.tables.pojos.Mark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;
    private final MarkFactory markFactory;
    private final LessonService lessonService;
    private final UUIDUtil uuidUtil;

    public MarkResponse saveMark(SaveMarkRequest request) {
        Mark mark = createMark(request.getMark(),
                request.getStudentId(),
                request.getInstructorId(),
                request.getLessonId());
        markRepository.save(mark);
        return markFactory.build(mark);
    }

    public List<MarkResponse> getMarksByStudentAndHisLessons(UUID studentId, List<UUID> lessonsIds) {
        List<Mark> marks = markRepository.findByStudentIdAndLessonId(studentId, lessonsIds);
        return markFactory.build(marks);
    }

    public List<MarkResponse> getMarksByStudentAndCourse(UUID studentId, UUID courseId) {
        List<LessonResponse> lessonsByCourse = lessonService.getLessonsByCourse(courseId);
        return getMarksByStudentAndHisLessons(studentId, lessonsByCourse.stream().map(LessonResponse::getLessonId).collect(toList()));
    }

    private Mark createMark(int markNumber, UUID studentId, UUID instructorId, UUID lessonId) {
        Mark mark = new Mark();
        mark.setId(uuidUtil.randomUUID());
        mark.setMark(markNumber);
        mark.setStudentId(studentId);
        mark.setInstructorId(instructorId);
        mark.setLessonId(lessonId);
        return mark;
    }

}
