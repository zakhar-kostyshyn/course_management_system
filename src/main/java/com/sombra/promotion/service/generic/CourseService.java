package com.sombra.promotion.service.generic;

import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.factory.generic.CourseFactory;
import com.sombra.promotion.repository.CourseRepository;
import com.sombra.promotion.tables.pojos.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseFactory courseFactory;
    private final UUIDUtil uuidUtil;

    public CourseResponse saveCourse(String courseName) {
        Course course = createCourse(courseName);
        courseRepository.save(course);
        return courseFactory.build(course);
    }

    private Course createCourse(String courseName) {
        Course course = new Course();
        course.setId(uuidUtil.randomUUID());
        course.setName(courseName);
        return course;
    }

}
