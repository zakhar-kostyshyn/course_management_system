package com.sombra.promotion.unit.service.common.validation;

import com.sombra.promotion.exception.NotFoundCourseBelongsForStudentException;
import com.sombra.promotion.exception.StudentNotAbleToUploadHomework;
import com.sombra.promotion.service.common.LessonService;
import com.sombra.promotion.service.common.manyToMany.StudentCourseService;
import com.sombra.promotion.service.common.validation.HomeworkValidationService;
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
class HomeworkValidationServiceTest {

    @Mock StudentCourseService studentCourseService;
    @Mock LessonService lessonService;
    @InjectMocks
    HomeworkValidationService homeworkValidationService;

    @Test
    void must_get_courseId_by_lessonId_and_assert_that_student_in_that_course() {

        // setUp
        UUID studentId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        when(lessonService.findCourseIdByLessonId(any())).thenReturn(courseId);

        // act
        homeworkValidationService.assertThatInstructorInCourse(studentId, lessonId);

        // verify
        verify(lessonService).findCourseIdByLessonId(lessonId);
        verify(studentCourseService).assertThatStudentInCourse(studentId, courseId);

    }


    @Test
    void must_catch_exception_from_student_assertion_and_rethrow_it_into_more_specific() {

        // setup
        UUID studentId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        when(lessonService.findCourseIdByLessonId(any())).thenReturn(courseId);
        doThrow(new NotFoundCourseBelongsForStudentException(studentId, courseId))
                .when(studentCourseService).assertThatStudentInCourse(any(), any());

        // act and verify
        assertThrows(StudentNotAbleToUploadHomework.class, () ->
                homeworkValidationService.assertThatInstructorInCourse(studentId, courseId)
        );

    }


}
