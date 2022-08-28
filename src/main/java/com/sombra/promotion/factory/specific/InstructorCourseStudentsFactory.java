package com.sombra.promotion.factory.specific;

import com.sombra.promotion.dto.response.InstructorCourseStudentsResponse;
import com.sombra.promotion.factory.generic.CourseFactory;
import com.sombra.promotion.factory.generic.UserFactory;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InstructorCourseStudentsFactory {

    private final CourseFactory courseFactory;
    private final UserFactory userFactory;

    public InstructorCourseStudentsResponse build(UUID instructorId,
                                                  UUID courseId,
                                                  List<User> students) {
        return InstructorCourseStudentsResponse
                .builder()
                .course(courseFactory.build(courseId))
                .students(userFactory.build(students))
                .instructor(userFactory.build(instructorId))
                .build();
    }

}
