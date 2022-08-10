package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.StudentsOfCourseAndInstructorResponse;
import com.sombra.promotion.factory.StudentsOfCourseAndInstructorFactory;
import com.sombra.promotion.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstructorStudentCourseService {

    private final StudentsOfCourseAndInstructorFactory studentsOfCourseAndInstructorFactory;
    private final UserService userService;
    private final InstructorCourseService instructorCourseService;

    public StudentsOfCourseAndInstructorResponse getAllStudentsByCourse(UUID courseId, SecurityUser authenticatedUser) {
        String instructor = authenticatedUser.getUsername();
        instructorCourseService.assertCourseBelongsToInstructor(courseId, instructor);
        return studentsOfCourseAndInstructorFactory.build(courseId, instructor, userService.getStudentsByCourseId(courseId));
    }

}
