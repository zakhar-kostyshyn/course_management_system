package com.sombra.promotion.unit.service.specific;

import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.dto.response.StudentCourseResponse;
import com.sombra.promotion.exception.CoursesForStudentOverflowException;
import com.sombra.promotion.repository.manyToMany.StudentCourseRepository;
import com.sombra.promotion.service.common.manyToMany.StudentCourseService;
import com.sombra.promotion.service.StudentSubscriptionOnCourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentSubscriptionOnCourseServiceTest {

    @Mock StudentCourseRepository studentCourseRepository;
    @Mock StudentCourseService studentCourseService;
    @InjectMocks
    StudentSubscriptionOnCourseService studentSubscriptionOnCourseService;


    @Test
    void must_subscribe_student_on_course() {

        // setup
        UUID courseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        CourseSubscriptionRequest request = new CourseSubscriptionRequest(courseId, studentId);
        ReflectionTestUtils.setField(studentSubscriptionOnCourseService, "maxCourseForStudent", 10);
        when(studentCourseRepository.countAmountStudentCourseBy(any())).thenReturn(1);
        StudentCourseResponse response = mock(StudentCourseResponse.class);
        when(studentCourseService.saveStudentCourse(any(), any())).thenReturn(response);

        // act
        StudentCourseResponse result = studentSubscriptionOnCourseService.subscribeStudentOnCourse(request);

        // verify
        verify(studentCourseRepository).countAmountStudentCourseBy(studentId);
        verify(studentCourseService).saveStudentCourse(studentId, courseId);
        assertThat(result, sameInstance(result));

    }


    @Test
    void must_throw_exception_when_try_to_subscribe_user_on_site_which_is_full() {

        // setup
        UUID courseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        CourseSubscriptionRequest request = new CourseSubscriptionRequest(courseId, studentId);
        ReflectionTestUtils.setField(studentSubscriptionOnCourseService, "maxCourseForStudent", 5);
        when(studentCourseRepository.countAmountStudentCourseBy(any())).thenReturn(6);

        // act
        // verify
        assertThrows(CoursesForStudentOverflowException.class, () ->
            studentSubscriptionOnCourseService.subscribeStudentOnCourse(request)
        );

    }

}
