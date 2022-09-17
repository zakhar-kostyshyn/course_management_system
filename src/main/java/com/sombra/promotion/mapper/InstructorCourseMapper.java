package com.sombra.promotion.mapper;

import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.response.CoursesOfInstructorResponse;
import com.sombra.promotion.mapper.common.CourseMapper;
import com.sombra.promotion.mapper.common.UserMapper;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.InstructorCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class InstructorCourseMapper {

    private final UserMapper userMapper;
    private final CourseMapper courseMapper;

    public InstructorCourseResponse build(InstructorCourse instructorCourse) {
        return InstructorCourseResponse.builder()
                .user(userMapper.build(instructorCourse.getInstructorId()))
                .course(courseMapper.build(instructorCourse.getCourseId()))
                .build();
    }

    public List<InstructorCourseResponse> build(List<InstructorCourse> instructorCourses) {
        return instructorCourses.stream().map(this::build).collect(toList());
    }

    public CoursesOfInstructorResponse build(UUID instructorId, List<Course> instructorCourses) {
        return CoursesOfInstructorResponse.builder()
                .user(userMapper.build(instructorId))
                .courses(
                        instructorCourses.stream()
                                .map(Course::getId)
                                .map(courseMapper::build)
                                .collect(toList())
                )
                .build();
    }

}
