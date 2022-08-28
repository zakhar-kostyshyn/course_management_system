package com.sombra.promotion.factory.specific;

import com.sombra.promotion.dto.response.CoursesOfStudentResponse;
import com.sombra.promotion.dto.response.StudentCourseResponse;
import com.sombra.promotion.factory.generic.CourseFactory;
import com.sombra.promotion.factory.generic.UserFactory;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.StudentCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentCourseFactory {

    private final UserFactory userFactory;
    private final CourseFactory courseFactory;

    public StudentCourseResponse build(StudentCourse studentCourse) {
        return StudentCourseResponse.builder()
                .user(userFactory.build(studentCourse.getStudentId()))
                .course(courseFactory.build(studentCourse.getCourseId()))
                .build();
    }

    public CoursesOfStudentResponse build(UUID studentId, List<Course> courses) {
        return CoursesOfStudentResponse.builder()
                .courses(courses.stream()
                        .map(Course::getId)
                        .map(courseFactory::build)
                        .collect(Collectors.toList())
                ).user(userFactory.build(studentId))
                .build();
    }

}
