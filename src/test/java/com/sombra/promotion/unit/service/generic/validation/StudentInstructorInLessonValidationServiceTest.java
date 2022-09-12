package com.sombra.promotion.unit.service.generic.validation;

import com.sombra.promotion.exception.LessonDontHaveStudentOrInstructorException;
import com.sombra.promotion.service.generic.LessonService;
import com.sombra.promotion.service.generic.validation.StudentInstructorInCourseValidationService;
import com.sombra.promotion.service.generic.validation.StudentInstructorInLessonValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentInstructorInLessonValidationServiceTest {

    @Mock
    StudentInstructorInCourseValidationService studentInstructorInCourseValidationService;
    @Mock LessonService lessonService;
    @InjectMocks
    StudentInstructorInLessonValidationService studentInstructorInLessonValidationService;

    @Test
    void must_get_lesson_by_id_and_check_if_student_and_instructor_exist_in_course() {

        UUID studentId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        when(lessonService.findCourseIdByLessonId(any())).thenReturn(courseId);

        // act
        studentInstructorInLessonValidationService.assertThatInstructorAndStudentHasLesson(studentId, instructorId, lessonId);

        // verify
        verify(lessonService).findCourseIdByLessonId(lessonId);
        verify(studentInstructorInCourseValidationService).assertThatInstructorAndStudentInCourse(studentId, instructorId, courseId);

    }


    @Test
    void must_catch_exception_and_rethrow_specific_when_student_or_instructor_not_exist_in_course() {

        UUID studentId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        when(lessonService.findCourseIdByLessonId(any())).thenReturn(courseId);
        doThrow(new RuntimeException()).when(studentInstructorInCourseValidationService).assertThatInstructorAndStudentInCourse(any(), any(), any());

        // act
        // verify
        assertThrows(LessonDontHaveStudentOrInstructorException.class, () ->
            studentInstructorInLessonValidationService.assertThatInstructorAndStudentHasLesson(studentId, instructorId, lessonId)
        );

    }

}