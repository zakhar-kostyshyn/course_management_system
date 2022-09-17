package com.sombra.promotion.mapper;

import com.sombra.promotion.dto.response.InstructorCourseStudentsResponse;
import com.sombra.promotion.mapper.common.CourseMapper;
import com.sombra.promotion.mapper.common.UserMapper;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InstructorCourseStudentsMapper {

    private final CourseMapper courseMapper;
    private final UserMapper userMapper;

    public InstructorCourseStudentsResponse build(UUID instructorId,
                                                  UUID courseId,
                                                  List<User> students) {
        return InstructorCourseStudentsResponse
                .builder()
                .course(courseMapper.build(courseId))
                .students(userMapper.build(students))
                .instructor(userMapper.build(instructorId))
                .build();
    }

}
