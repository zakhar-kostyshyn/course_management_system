package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.InstructorCourseDetails;
import com.sombra.promotion.dto.details.CoursesOfInstructorDetails;
import com.sombra.promotion.tables.pojos.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class InstructorCourseDetailsFactory {

    private final UserDetailsFactory userDetailsFactory;
    private final CourseDetailsFactory courseDetailsFactory;

    public InstructorCourseDetails build(String username, String courseName) {
        return InstructorCourseDetails.builder()
                .userDetails(userDetailsFactory.build(username))
                .courseDetails(courseDetailsFactory.build(courseName))
                .build();
    }

    public CoursesOfInstructorDetails build(String instructorUsername, List<Course> instructorCourses) {
        return CoursesOfInstructorDetails.builder()
                .username(userDetailsFactory.build(instructorUsername))
                .courses(
                        instructorCourses.stream()
                                .map(Course::getId)
                                .map(courseDetailsFactory::build)
                                .collect(toList())
                )
                .build();
    }

}
