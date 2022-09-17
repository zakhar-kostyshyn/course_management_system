package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.dto.response.StudentCourseLessonsResponse;
import com.sombra.promotion.mapper.LessonsOfCourseAndStudentMapper;
import com.sombra.promotion.service.common.LessonService;
import com.sombra.promotion.service.common.manyToMany.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StudentLessonService {

    private final LessonsOfCourseAndStudentMapper lessonsOfCourseAndStudentMapper;
    private final StudentCourseService studentCourseService;
    private final LessonService lessonService;

    public StudentCourseLessonsResponse getLessonsOfStudentInCourse(UUID studentId, UUID courseId) {
        studentCourseService.assertThatStudentInCourse(studentId, courseId);
        List<LessonResponse> lessonsByCourse = lessonService.getLessonsByCourse(courseId);
        return lessonsOfCourseAndStudentMapper.build(studentId, courseId, lessonsByCourse.stream().map(LessonResponse::getLessonId).collect(toList()));
    }

}
