package com.sombra.promotion.service;

import com.sombra.promotion.controller.student.response.StudentCourseLessonResponse;
import com.sombra.promotion.controller.student.response.StudentCourseResponse;
import com.sombra.promotion.repository.CourseRepository;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.repository.MarkRepository;
import com.sombra.promotion.tables.pojos.Mark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentDetailsService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final MarkRepository markRepository;

    // TODO rewrite on JOOQ level
    public List<StudentCourseResponse> getAllStudentCourses(String student) {
        return courseRepository.selectCoursesByStudent(student).stream()
                .map(course -> {
                    List<StudentCourseLessonResponse> lessonsResponse = lessonRepository.selectLessonsByCourse(course.getName()).stream()
                            .map(lesson -> {
                                Mark mark = markRepository.selectMarkByStudentUsernameAndLessonId(student, lesson.getId());
                                return new StudentCourseLessonResponse(lesson.getName(), mark.getMark());
                            }).collect(Collectors.toList());
                    return new StudentCourseResponse(course.getName(), lessonsResponse);
                }).collect(Collectors.toList());

    }

}
