package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.CourseDetails;
import com.sombra.promotion.repository.CourseRepository;
import com.sombra.promotion.tables.pojos.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CourseDetailsFactory {

    private final CourseRepository courseRepository;

    public CourseDetails build(String courseName) {
        return build(courseRepository.selectCourseBy(courseName));
    }

    public CourseDetails build(UUID id) {
        return build(courseRepository.selectCourseBy(id));
    }

    private CourseDetails build(Course course) {
        return CourseDetails.builder()
                .id(course.getId())
                .courseName(course.getName())
                .build();
    }

}
