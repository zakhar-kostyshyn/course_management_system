package com.sombra.promotion.factory;

import com.sombra.promotion.dto.response.StudentsOfCourseAndInstructorResponse;
import com.sombra.promotion.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StudentsOfCourseAndInstructorFactory {

    private final CourseFactory courseFactory;
    private final UserFactory userFactory;

    public StudentsOfCourseAndInstructorResponse build(
            UUID courseId,
            String instructor,
            List<UserResponse> students
    ) {
        return StudentsOfCourseAndInstructorResponse
                .builder()
                .courseResponse(courseFactory.build(courseId))
                .students(students)
                .instructor(userFactory.build(instructor))
                .build();
    }

}
