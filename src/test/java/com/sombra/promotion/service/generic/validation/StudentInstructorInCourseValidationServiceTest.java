package com.sombra.promotion.service.generic.validation;

import com.sombra.promotion.exception.CourseDontHaveStudentOrInstructorException;
import com.sombra.promotion.service.generic.manyToMany.InstructorCourseService;
import com.sombra.promotion.service.generic.manyToMany.StudentCourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class StudentInstructorInCourseValidationServiceTest {

    @Mock InstructorCourseService instructorCourseService;
    @Mock StudentCourseService studentCourseService;
    @InjectMocks StudentInstructorInCourseValidationService studentInstructorInCourseValidationService;

    @Test
    void must_assert_that_instructor_with_student_in_course() {

        // setup
        UUID studentId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        // act
        studentInstructorInCourseValidationService.assertThatInstructorAndStudentInCourse(studentId, instructorId, courseId);

        // verify
        instructorCourseService.assertThatInstructorInCourse(instructorId, courseId);
        studentCourseService.assertThatStudentInCourse(studentId, courseId);

    }

    @Test
    void must_catch_exception_when_instructor_not_exist_in_course_and_rethrow_specific_exception() {

        // setup
        UUID studentId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        doThrow(new RuntimeException()).when(instructorCourseService).assertThatInstructorInCourse(any(), any());

        // verify
        // act
        assertThrows(CourseDontHaveStudentOrInstructorException.class, () ->
            studentInstructorInCourseValidationService.assertThatInstructorAndStudentInCourse(studentId, instructorId, courseId)
        );

    }

    @Test
    void must_catch_exception_when_student_not_exist_in_course_and_rethrow_specific_exception() {

        // setup
        UUID studentId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        doThrow(new RuntimeException()).when(studentCourseService).assertThatStudentInCourse(any(), any());

        // verify
        // act
        assertThrows(CourseDontHaveStudentOrInstructorException.class, () ->
                studentInstructorInCourseValidationService.assertThatInstructorAndStudentInCourse(studentId, instructorId, courseId)
        );

    }

}