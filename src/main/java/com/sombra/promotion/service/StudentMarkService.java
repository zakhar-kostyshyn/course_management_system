package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.dto.response.MarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StudentMarkService {

    private final MarkService markService;
    private final LessonService lessonService;

    public List<MarkResponse> getMarksByStudentAndCourse(String student, String course) {
        List<LessonResponse> lessonsByCourse = lessonService.getLessonsByCourse(course);
        return markService.getMarksForStudents(student, lessonsByCourse.stream().map(LessonResponse::getLessonId).collect(toList()));
    }

}
