package com.sombra.promotion.factory.generic;

import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.interfaces.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.CourseRepository;
import com.sombra.promotion.tables.pojos.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseFactory extends AbstractResponseFactory<Course, CourseResponse, CourseRepository> {

    private final CourseRepository courseRepository;

    @Override
    public CourseResponse build(Course model) {
        return CourseResponse.builder()
                .id(model.getId())
                .courseName(model.getName())
                .build();
    }

    @Override
    public CourseRepository getDao() {
        return courseRepository;
    }

}
