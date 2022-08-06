package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.dto.details.StudentCourseDetails;
import com.sombra.promotion.exception.NotFoundCourseBelongsForInstructorException;
import com.sombra.promotion.factory.StudentCourseDetailsFactory;
import com.sombra.promotion.repository.StudentCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentCourseDetailsFactory studentCourseDetailsFactory;

    public StudentCourseDetails subscribeStudentOnCourse(CourseSubscriptionRequest request) {
        studentCourseRepository.setStudentForCourse(request.getStudent(), request.getCourse());
        return studentCourseDetailsFactory.build(request.getStudent(), request.getCourse());
    }

    public void assertCourseContainsStudent(UUID courseId, String student) {
        boolean isExist = studentCourseRepository.existStudentCourseBy(courseId, student);
        if (!isExist) throw new NotFoundCourseBelongsForInstructorException(format("Course with ID: %s and student with username: %s", courseId, student));
    }

}
