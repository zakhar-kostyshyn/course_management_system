package com.sombra.promotion.mapper;

import com.sombra.promotion.dto.response.CoursesOfStudentResponse;
import com.sombra.promotion.dto.response.StudentCourseResponse;
import com.sombra.promotion.mapper.common.CourseMapper;
import com.sombra.promotion.mapper.common.UserMapper;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.StudentCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentCourseMapper {

    private final UserMapper userMapper;
    private final CourseMapper courseMapper;

    public StudentCourseResponse build(StudentCourse studentCourse) {
        return StudentCourseResponse.builder()
                .user(userMapper.build(studentCourse.getStudentId()))
                .course(courseMapper.build(studentCourse.getCourseId()))
                .build();
    }

    public CoursesOfStudentResponse build(UUID studentId, List<Course> courses) {
        return CoursesOfStudentResponse.builder()
                .courses(courses.stream()
                        .map(Course::getId)
                        .map(courseMapper::build)
                        .collect(Collectors.toList())
                ).user(userMapper.build(studentId))
                .build();
    }

}
