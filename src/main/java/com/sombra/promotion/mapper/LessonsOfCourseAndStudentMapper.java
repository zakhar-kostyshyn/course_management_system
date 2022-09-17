package com.sombra.promotion.mapper;

import com.sombra.promotion.dto.response.StudentCourseLessonsResponse;
import com.sombra.promotion.mapper.common.CourseMapper;
import com.sombra.promotion.mapper.common.LessonMapper;
import com.sombra.promotion.mapper.common.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class LessonsOfCourseAndStudentMapper {

    private final UserMapper userMapper;
    private final CourseMapper courseMapper;
    private final LessonMapper lessonMapper;

    public StudentCourseLessonsResponse build(UUID studentId,
                                              UUID courseId,
                                              List<UUID> lessonIds) {
        return StudentCourseLessonsResponse.builder()
                .lessons(lessonIds.stream()
                        .map(lessonMapper::build)
                        .collect(toList())
                ).user(userMapper.build(studentId))
                .course(courseMapper.build(courseId))
                .build();
    }

}
