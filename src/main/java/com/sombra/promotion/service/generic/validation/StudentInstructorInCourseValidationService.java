package com.sombra.promotion.service.generic.validation;

import com.sombra.promotion.exception.CourseDontHaveStudentOrInstructorException;
import com.sombra.promotion.service.generic.manyToMany.InstructorCourseService;
import com.sombra.promotion.service.generic.manyToMany.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentInstructorInCourseValidationService {

    private final InstructorCourseService instructorCourseService;
    private final StudentCourseService studentCourseService;

    @NonNull
    public void assertThatInstructorAndStudentInCourse(UUID studentId, UUID instructorId, UUID courseId) {
        try {
            instructorCourseService.assertThatInstructorInCourse(instructorId, courseId);
            studentCourseService.assertThatStudentInCourse(studentId, courseId);
        } catch (Exception e) {
            throw new CourseDontHaveStudentOrInstructorException(studentId, instructorId, instructorId, e);
        }
    }

}
