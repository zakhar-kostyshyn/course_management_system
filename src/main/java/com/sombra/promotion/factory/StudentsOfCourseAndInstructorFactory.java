package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.StudentsOfCourseAndInstructorDetails;
import com.sombra.promotion.dto.details.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StudentsOfCourseAndInstructorFactory {

    private final CourseDetailsFactory courseDetailsFactory;
    private final UserDetailsFactory userDetailsFactory;

    public StudentsOfCourseAndInstructorDetails build(
            UUID courseId,
            String instructor,
            List<UserDetails> students
    ) {
        return StudentsOfCourseAndInstructorDetails
                .builder()
                .courseDetails(courseDetailsFactory.build(courseId))
                .students(students)
                .instructor(userDetailsFactory.build(instructor))
                .build();
    }

}
