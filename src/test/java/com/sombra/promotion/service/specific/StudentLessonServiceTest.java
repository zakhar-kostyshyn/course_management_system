package com.sombra.promotion.service.specific;

import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.dto.response.StudentCourseLessonsResponse;
import com.sombra.promotion.factory.specific.LessonsOfCourseAndStudentFactory;
import com.sombra.promotion.service.generic.LessonService;
import com.sombra.promotion.service.generic.manyToMany.StudentCourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentLessonServiceTest {

    @Mock LessonsOfCourseAndStudentFactory lessonsOfCourseAndStudentFactory;
    @Mock StudentCourseService studentCourseService;
    @Mock LessonService lessonService;
    @InjectMocks StudentLessonService studentLessonService;

    @Test
    void must_assert_that_student_in_course() {

        // setup
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        when(lessonService.getLessonsByCourse(any())).thenReturn(List.of());

        // act
        studentLessonService.getLessonsOfStudentInCourse(studentId, courseId);

        // verify
        verify(studentCourseService).assertThatStudentInCourse(studentId, courseId);

    }

    @Test
    void must_get_lessons_by_course_build_response_and_return() {

        // setup
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        LessonResponse lessonResponse = mock(LessonResponse.class);
        when(lessonResponse.getLessonId()).thenReturn(lessonId);
        when(lessonService.getLessonsByCourse(any())).thenReturn(List.of(lessonResponse));
        StudentCourseLessonsResponse response = mock(StudentCourseLessonsResponse.class);
        when(lessonsOfCourseAndStudentFactory.build(any(), any(), anyList())).thenReturn(response);

        // act
        StudentCourseLessonsResponse result = studentLessonService.getLessonsOfStudentInCourse(studentId, courseId);

        // verify
        verify(lessonService).getLessonsByCourse(courseId);
        verify(lessonsOfCourseAndStudentFactory).build(studentId, courseId, List.of(lessonId));
        assertThat(result, sameInstance(response));

    }

}