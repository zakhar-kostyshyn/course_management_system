package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.dto.response.StudentCourseResponse;
import com.sombra.promotion.exception.CoursesForStudentOverflowException;
import com.sombra.promotion.repository.manyToMany.StudentCourseRepository;
import com.sombra.promotion.service.common.manyToMany.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentSubscriptionOnCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentCourseService studentCourseService;
    @Value("${app.max-course-for-student}") private int maxCourseForStudent;

    public StudentCourseResponse subscribeStudentOnCourse(CourseSubscriptionRequest request) {
        int amount = studentCourseRepository.countAmountStudentCourseBy(request.getStudentId());
        if (amount > maxCourseForStudent) throw new CoursesForStudentOverflowException(maxCourseForStudent);
        return studentCourseService.saveStudentCourse(request.getStudentId(), request.getCourseId());
    }


}
