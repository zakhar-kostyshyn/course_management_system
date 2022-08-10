package com.sombra.promotion.service;

import com.sombra.promotion.dto.details.LessonDetails;
import com.sombra.promotion.dto.details.MarkDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StudentMarkService {

    private final MarkService markService;
    private final LessonService lessonService;

    public List<MarkDetails> getMarksByStudentAndCourse(String student, String course) {
        List<LessonDetails> lessonsByCourse = lessonService.getLessonsByCourse(course);
        return markService.getMarksForStudents(student, lessonsByCourse.stream().map(LessonDetails::getLessonId).collect(toList()));
    }

}
