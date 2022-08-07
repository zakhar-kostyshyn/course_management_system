package com.sombra.promotion.service;

import com.sombra.promotion.dto.details.StudentCourseDetails;
import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.exception.CoursesForStudentOverflowException;
import com.sombra.promotion.exception.NotFoundCourseBelongsForStudentException;
import com.sombra.promotion.factory.StudentCourseDetailsFactory;
import com.sombra.promotion.repository.StudentCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentCourseDetailsFactory studentCourseDetailsFactory;
    @Value("${app.max-course-for-student}") private int maxCourseForStudent;

    public StudentCourseDetails subscribeStudentOnCourse(CourseSubscriptionRequest request) {

        int amount = studentCourseRepository.countAmountStudentCourseBy(request.getStudent());
        if (amount > maxCourseForStudent) throw new CoursesForStudentOverflowException(maxCourseForStudent);

        studentCourseRepository.setStudentForCourse(request.getStudent(), request.getCourse());
        return studentCourseDetailsFactory.build(request.getStudent(), request.getCourse());
    }

    public void assertCourseContainsStudent(UUID courseId, String student) {
        boolean isExist = studentCourseRepository.existStudentCourseBy(courseId, student);
        if (!isExist) throw new NotFoundCourseBelongsForStudentException(courseId, student);
    }

}
