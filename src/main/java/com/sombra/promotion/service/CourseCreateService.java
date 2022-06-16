package com.sombra.promotion.service;

import com.sombra.promotion.controller.instructor.request.CreateCourseRequest;
import com.sombra.promotion.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseCreateService {

    private final DomainRepository repository;

    public void createCourse(CreateCourseRequest request) {
        repository.insertCourse(request.getCourse(), request.getInstructor());
    }
}
