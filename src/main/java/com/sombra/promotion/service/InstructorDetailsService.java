package com.sombra.promotion.service;

import com.sombra.promotion.controller.instructor.response.InstructorCourseResponse;
import com.sombra.promotion.controller.instructor.response.InstructorCourseStudentResponse;
import com.sombra.promotion.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorDetailsService {


    private final DomainRepository repository;

    public List<InstructorCourseResponse> getInstructorCourseDetails(String instructor) {
        return repository.selectCoursesByInstructor(instructor).stream()
                .map(course ->
                        new InstructorCourseResponse(
                                course.getId(),
                                repository.selectStudentsByCourseId(course.getId()).stream()
                                        .map(user -> new InstructorCourseStudentResponse(user.getId(), user.getUsername()))
                                        .collect(Collectors.toList()),
                                course.getName()
                        )
                ).collect(Collectors.toList());

    }
}
