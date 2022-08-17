package com.sombra.promotion.service.specific;

import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.dto.response.StudentCourseLessonsResponse;
import com.sombra.promotion.factory.specific.LessonsOfCourseAndStudentFactory;
import com.sombra.promotion.service.generic.LessonService;
import com.sombra.promotion.service.generic.manyToMany.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StudentLessonService {

    private final LessonsOfCourseAndStudentFactory lessonsOfCourseAndStudentFactory;
    private final StudentCourseService studentCourseService;
    private final LessonService lessonService;

    public StudentCourseLessonsResponse getLessonsInCourseOfStudent(UUID studentId, UUID courseId) {
        studentCourseService.assertThatStudentInCourse(studentId, courseId);
        List<LessonResponse> lessonsByCourse = lessonService.getLessonsByCourse(courseId);
        return lessonsOfCourseAndStudentFactory.build(studentId, courseId, lessonsByCourse.stream().map(LessonResponse::getLessonId).collect(toList()));
    }

}
