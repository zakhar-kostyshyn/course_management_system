package com.sombra.promotion.factory;

import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.response.CoursesOfInstructorResponse;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.InstructorCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class InstructorCourseFactory {

    private final UserFactory userFactory;
    private final CourseFactory courseFactory;

    public InstructorCourseResponse build(InstructorCourse instructorCourse) {
        return InstructorCourseResponse.builder()
                .userResponse(userFactory.build(instructorCourse.getCourseId()))
                .courseResponse(courseFactory.build(instructorCourse.getInstructorId()))
                .build();
    }

    public List<InstructorCourseResponse> build(List<InstructorCourse> instructorCourses) {
        return instructorCourses.stream().map(this::build).collect(toList());
    }

    public CoursesOfInstructorResponse build(String instructorUsername, List<Course> instructorCourses) {
        return CoursesOfInstructorResponse.builder()
                .username(userFactory.build(instructorUsername))
                .courses(
                        instructorCourses.stream()
                                .map(Course::getId)
                                .map(courseFactory::build)
                                .collect(toList())
                )
                .build();
    }

}
