package com.sombra.promotion.service;

import com.sombra.promotion.controller.student.response.StudentCourseLessonResponse;
import com.sombra.promotion.controller.student.response.StudentCourseResponse;
import com.sombra.promotion.repository.DomainRepository;
import com.sombra.promotion.tables.pojos.Mark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentDetailsService {

    private final DomainRepository domainRepository;

    // TODO rewrite on JOOQ level
    public List<StudentCourseResponse> getAllStudentCourses(String student) {
        return domainRepository.selectCoursesByStudent(student).stream()
                .map(course -> {
                    List<StudentCourseLessonResponse> lessonsResponse = domainRepository.selectLessonsByCourse(course.getName()).stream()
                            .map(lesson -> {
                                Mark mark = domainRepository.selectMarkByStudentUsernameAndLessonId(student, lesson.getId());
                                return new StudentCourseLessonResponse(lesson.getName(), mark.getMark());
                            }).collect(Collectors.toList());
                    return new StudentCourseResponse(course.getName(), lessonsResponse);
                }).collect(Collectors.toList());

    }

}
