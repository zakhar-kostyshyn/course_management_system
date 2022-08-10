package com.sombra.promotion.factory;

import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.repository.CourseRepository;
import com.sombra.promotion.tables.pojos.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CourseFactory {

    private final CourseRepository courseRepository;

    public CourseResponse build(String courseName) {
        return build(courseRepository.selectCourseBy(courseName));
    }

    public CourseResponse build(UUID id) {
        return build(courseRepository.selectCourseBy(id));
    }

    private CourseResponse build(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getName())
                .build();
    }

}
