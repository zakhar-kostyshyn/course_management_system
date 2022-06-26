package com.sombra.promotion.service;

import com.sombra.promotion.controller.instructor.request.CreateCourseRequest;
import com.sombra.promotion.repository.InstructorCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseCreateService {

    private final InstructorCourseRepository instructorCourseRepository;

    public void createCourse(CreateCourseRequest request) {
        instructorCourseRepository.insertCourse(request.getCourse(), request.getInstructor());
    }
}
