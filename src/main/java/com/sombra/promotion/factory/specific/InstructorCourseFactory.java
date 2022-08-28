package com.sombra.promotion.factory.specific;

import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.response.CoursesOfInstructorResponse;
import com.sombra.promotion.factory.generic.CourseFactory;
import com.sombra.promotion.factory.generic.UserFactory;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.InstructorCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class InstructorCourseFactory {

    private final UserFactory userFactory;
    private final CourseFactory courseFactory;

    public InstructorCourseResponse build(InstructorCourse instructorCourse) {
        return InstructorCourseResponse.builder()
                .user(userFactory.build(instructorCourse.getInstructorId()))
                .course(courseFactory.build(instructorCourse.getCourseId()))
                .build();
    }

    public List<InstructorCourseResponse> build(List<InstructorCourse> instructorCourses) {
        return instructorCourses.stream().map(this::build).collect(toList());
    }

    public CoursesOfInstructorResponse build(UUID instructorId, List<Course> instructorCourses) {
        return CoursesOfInstructorResponse.builder()
                .user(userFactory.build(instructorId))
                .courses(
                        instructorCourses.stream()
                                .map(Course::getId)
                                .map(courseFactory::build)
                                .collect(toList())
                )
                .build();
    }

}
