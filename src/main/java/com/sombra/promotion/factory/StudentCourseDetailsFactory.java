package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.CoursesOfStudentDetails;
import com.sombra.promotion.dto.details.StudentCourseDetails;
import com.sombra.promotion.tables.pojos.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentCourseDetailsFactory {

    private final UserDetailsFactory userDetailsFactory;
    private final CourseDetailsFactory courseDetailsFactory;

    public StudentCourseDetails build(String student, String course) {
        return StudentCourseDetails.builder()
                .userDetails(userDetailsFactory.build(student))
                .courseDetails(courseDetailsFactory.build(course))
                .build();
    }

    public CoursesOfStudentDetails build(String student, List<Course> courses) {
        return CoursesOfStudentDetails.builder()
                .courses(courses.stream()
                        .map(Course::getId)
                        .map(courseDetailsFactory::build)
                        .collect(Collectors.toList())
                ).student(userDetailsFactory.build(student))
                .build();
    }

}
