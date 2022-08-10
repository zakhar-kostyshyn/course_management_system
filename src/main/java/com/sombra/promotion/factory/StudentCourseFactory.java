package com.sombra.promotion.factory;

import com.sombra.promotion.dto.response.CoursesOfStudentResponse;
import com.sombra.promotion.dto.response.StudentCourseResponse;
import com.sombra.promotion.tables.pojos.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentCourseFactory {

    private final UserFactory userFactory;
    private final CourseFactory courseFactory;

    public StudentCourseResponse build(String student, String course) {
        return StudentCourseResponse.builder()
                .userResponse(userFactory.build(student))
                .courseResponse(courseFactory.build(course))
                .build();
    }

    public CoursesOfStudentResponse build(String student, List<Course> courses) {
        return CoursesOfStudentResponse.builder()
                .courses(courses.stream()
                        .map(Course::getId)
                        .map(courseFactory::build)
                        .collect(Collectors.toList())
                ).student(userFactory.build(student))
                .build();
    }

}
