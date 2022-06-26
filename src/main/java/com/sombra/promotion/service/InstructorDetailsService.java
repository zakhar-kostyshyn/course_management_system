package com.sombra.promotion.service;

import com.sombra.promotion.controller.instructor.response.InstructorCourseResponse;
import com.sombra.promotion.controller.instructor.response.InstructorCourseStudentResponse;
import com.sombra.promotion.repository.CourseRepository;
import com.sombra.promotion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorDetailsService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public List<InstructorCourseResponse> getInstructorCourseDetails(String instructor) {
        return courseRepository.selectCoursesByInstructor(instructor).stream()
                .map(course ->
                        new InstructorCourseResponse(
                                course.getId(),
                                userRepository.selectStudentsByCourseId(course.getId()).stream()
                                        .map(user -> new InstructorCourseStudentResponse(user.getId(), user.getUsername()))
                                        .collect(Collectors.toList()),
                                course.getName()
                        )
                ).collect(Collectors.toList());

    }
}
