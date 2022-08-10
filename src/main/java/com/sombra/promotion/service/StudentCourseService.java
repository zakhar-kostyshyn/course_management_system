package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.StudentCourseResponse;
import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.exception.CoursesForStudentOverflowException;
import com.sombra.promotion.exception.NotFoundCourseBelongsForStudentException;
import com.sombra.promotion.factory.StudentCourseFactory;
import com.sombra.promotion.repository.StudentCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentCourseFactory studentCourseFactory;
    @Value("${app.max-course-for-student}") private int maxCourseForStudent;

    public StudentCourseResponse subscribeStudentOnCourse(CourseSubscriptionRequest request) {

        int amount = studentCourseRepository.countAmountStudentCourseBy(request.getStudent());
        if (amount > maxCourseForStudent) throw new CoursesForStudentOverflowException(maxCourseForStudent);

        studentCourseRepository.setStudentForCourse(request.getStudent(), request.getCourse());
        return studentCourseFactory.build(request.getStudent(), request.getCourse());
    }

    public void assertCourseContainsStudent(UUID courseId, String student) {
        boolean isExist = studentCourseRepository.existStudentCourseBy(courseId, student);
        if (!isExist) throw new NotFoundCourseBelongsForStudentException(courseId, student);
    }

}
