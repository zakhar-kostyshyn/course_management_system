package com.sombra.promotion.factory.generic;

import com.sombra.promotion.dto.response.CourseMarkResponse;
import com.sombra.promotion.interfaces.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.CourseMarkRepository;
import com.sombra.promotion.tables.pojos.CourseMark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMarkFactory extends AbstractResponseFactory<CourseMark, CourseMarkResponse, CourseMarkRepository> {

    private final CourseMarkRepository courseMarkRepository;
    private final CourseFactory courseFactory;
    private final UserFactory userFactory;

    @Override
    public CourseMarkRepository getDao() {
        return courseMarkRepository;
    }

    @Override
    public CourseMarkResponse build(CourseMark courseMark) {
        return CourseMarkResponse.builder()
                .id(courseMark.getId())
                .mark(courseMark.getMark())
                .student(userFactory.build(courseMark.getStudentId()))
                .course(courseFactory.build(courseMark.getCourseId()))
                .build();
    }

}
