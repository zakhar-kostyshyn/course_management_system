package com.sombra.promotion.unit.service.generic.manyToMany.transition;

import com.sombra.promotion.dto.response.InstructorCourseStudentsResponse;
import com.sombra.promotion.factory.specific.InstructorCourseStudentsFactory;
import com.sombra.promotion.service.generic.manyToMany.InstructorCourseService;
import com.sombra.promotion.service.generic.manyToMany.StudentCourseService;
import com.sombra.promotion.service.generic.manyToMany.transition.InstructorStudentService;
import com.sombra.promotion.tables.pojos.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorStudentServiceTest {

    @Mock InstructorCourseService instructorCourseService;
    @Mock StudentCourseService studentCourseService;
    @Mock InstructorCourseStudentsFactory instructorCourseStudentsFactory;
    @InjectMocks
    InstructorStudentService instructorStudentService;

    UUID instructorId;
    UUID courseId;
    List<User> studentsByCourse;
    InstructorCourseStudentsResponse response;

    @BeforeEach
    void setUp() {
        instructorId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        studentsByCourse = List.of(mock(User.class), mock(User.class));
        response = mock(InstructorCourseStudentsResponse.class);
    }

    @Test
    void must_assert_that_instructor_in_course() {
        instructorStudentService.getAllStudentsInCourseForInstructor(instructorId, courseId);
        verify(instructorCourseService).assertThatInstructorInCourse(instructorId, courseId);
    }

    @Test
    void must_get_students_by_course() {
        instructorStudentService.getAllStudentsInCourseForInstructor(instructorId, courseId);
        verify(studentCourseService).getStudentsByCourse(courseId);
    }

    @Test
    void must_build_response_with_instructor_course_and_student_info() {
        when(studentCourseService.getStudentsByCourse(any())).thenReturn(studentsByCourse);
        instructorStudentService.getAllStudentsInCourseForInstructor(instructorId, courseId);
        verify(instructorCourseStudentsFactory).build(instructorId, courseId, studentsByCourse);
    }

    @Test
    void must_return_response_with_instructor_course_and_student_info() {
        when(studentCourseService.getStudentsByCourse(any())).thenReturn(studentsByCourse);
        when(instructorCourseStudentsFactory.build(any(), any(), anyList())).thenReturn(response);
        var result = instructorStudentService.getAllStudentsInCourseForInstructor(instructorId, courseId);
        assertThat(result, sameInstance(response));
    }

}