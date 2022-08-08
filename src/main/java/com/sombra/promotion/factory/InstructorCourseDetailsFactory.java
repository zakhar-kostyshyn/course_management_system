package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.InstructorCourseDetails;
import com.sombra.promotion.dto.details.CoursesOfInstructorDetails;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.InstructorCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class InstructorCourseDetailsFactory {

    private final UserDetailsFactory userDetailsFactory;
    private final CourseDetailsFactory courseDetailsFactory;

    public InstructorCourseDetails build(InstructorCourse instructorCourse) {
        return InstructorCourseDetails.builder()
                .userDetails(userDetailsFactory.build(instructorCourse.getCourseId()))
                .courseDetails(courseDetailsFactory.build(instructorCourse.getInstructorId()))
                .build();
    }

    public List<InstructorCourseDetails> build(List<InstructorCourse> instructorCourses) {
        return instructorCourses.stream().map(this::build).collect(toList());
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
