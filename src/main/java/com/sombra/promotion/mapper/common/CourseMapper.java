package com.sombra.promotion.mapper.common;

import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.abstraction.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.CourseRepository;
import com.sombra.promotion.tables.pojos.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper extends AbstractResponseFactory<Course, CourseResponse, CourseRepository> {

    private final CourseRepository courseRepository;

    @Override
    public CourseResponse build(Course model) {
        return CourseResponse.builder()
                .courseId(model.getId())
                .courseName(model.getName())
                .build();
    }

    @Override
    public CourseRepository getRepository() {
        return courseRepository;
    }

}
