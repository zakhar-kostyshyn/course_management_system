package com.sombra.promotion.mapper.common;

import com.sombra.promotion.abstraction.factory.AbstractResponseFactory;
import com.sombra.promotion.dto.response.CourseMarkResponse;
import com.sombra.promotion.repository.CourseMarkRepository;
import com.sombra.promotion.tables.pojos.CourseMark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMarkMapper extends AbstractResponseFactory<CourseMark, CourseMarkResponse, CourseMarkRepository> {

    private final CourseMarkRepository courseMarkRepository;
    private final CourseMapper courseMapper;
    private final UserMapper userMapper;

    @Override
    public CourseMarkRepository getRepository() {
        return courseMarkRepository;
    }

    @Override
    public CourseMarkResponse build(CourseMark courseMark) {
        return CourseMarkResponse.builder()
                .id(courseMark.getId())
                .courseMark(courseMark.getMark())
                .student(userMapper.build(courseMark.getStudentId()))
                .course(courseMapper.build(courseMark.getCourseId()))
                .isCoursePassed(courseMark.getCoursePassed())
                .build();
    }

}
