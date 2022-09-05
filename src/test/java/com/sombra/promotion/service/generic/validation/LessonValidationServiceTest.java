package com.sombra.promotion.service.generic.validation;

import com.sombra.promotion.exception.InstructorNotAbleToCreateLesson;
import com.sombra.promotion.exception.NotFoundCourseBelongsForInstructorException;
import com.sombra.promotion.service.generic.manyToMany.InstructorCourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LessonValidationServiceTest {

    @Mock InstructorCourseService instructorCourseService;
    @InjectMocks LessonValidationService lessonValidationService;


    @Test
    void must_assert_that_instructor_in_course() {

        // setup
        UUID instructorId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        // act
        lessonValidationService.assertThatInstructorInCourse(instructorId, courseId);

        // verify
        verify(instructorCourseService).assertThatInstructorInCourse(instructorId, courseId);

    }


    @Test
    void must_catch_assertion_error_and_rethrow_more_specific() {

        // setup
        UUID instructorId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        doThrow(new NotFoundCourseBelongsForInstructorException(instructorId, courseId))
                .when(instructorCourseService).assertThatInstructorInCourse(instructorId, courseId);

        // verify
        // act
        assertThrows(InstructorNotAbleToCreateLesson.class, () ->
                lessonValidationService.assertThatInstructorInCourse(instructorId, courseId)
        );

    }


}