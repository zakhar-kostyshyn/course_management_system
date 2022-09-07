package com.sombra.promotion.service.specific;

import com.sombra.promotion.dto.request.FinishCourseRequest;
import com.sombra.promotion.dto.response.CourseMarkResponse;
import com.sombra.promotion.dto.response.FinishCourseResponse;
import com.sombra.promotion.dto.response.MarkResponse;
import com.sombra.promotion.service.generic.CourseMarkService;
import com.sombra.promotion.service.generic.MarkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinishCourseServiceTest {

    @Mock CourseMarkService courseMarkService;
    @Mock MarkService markService;
    @InjectMocks FinishCourseService finishCourseService;

    @Test
    void must_get_marks_by_student_and_course_and_calculate_average_mark_for_course_and_then_create_passed_course_mark() {

        // setup
        UUID courseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        FinishCourseRequest request = new FinishCourseRequest(courseId, studentId);
        MarkResponse mark1 = mock(MarkResponse.class);
        MarkResponse mark2 = mock(MarkResponse.class);
        List<MarkResponse> marks = List.of(mark1, mark2);
        CourseMarkResponse courseMark = mock(CourseMarkResponse.class);

        ReflectionTestUtils.setField(finishCourseService, "minimumCoursePassMark", 15);
        when(mark1.getMarkValue()).thenReturn(10);
        when(mark2.getMarkValue()).thenReturn(20);
        when(markService.getMarksByStudentAndCourse(any(), any())).thenReturn(marks);
        when(courseMarkService.savePassedCourseMark(anyDouble(), any(), any())).thenReturn(courseMark);

        // act
        FinishCourseResponse result = finishCourseService.finishCourse(request);

        // verify
        verify(markService).getMarksByStudentAndCourse(studentId, courseId);
        verify(courseMarkService).savePassedCourseMark(15, studentId, courseId);
        assertThat(result.getCourseMark(), is(courseMark));
        assertThat(result.getMarks(), is(marks));

    }

    @Test
    void must_get_marks_by_student_and_course_and_calculate_average_mark_for_course_and_then_create_nonpassed_course_mark() {

        // setup
        UUID courseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        FinishCourseRequest request = new FinishCourseRequest(courseId, studentId);
        MarkResponse mark1 = mock(MarkResponse.class);
        MarkResponse mark2 = mock(MarkResponse.class);
        List<MarkResponse> marks = List.of(mark1, mark2);
        CourseMarkResponse courseMark = mock(CourseMarkResponse.class);

        ReflectionTestUtils.setField(finishCourseService, "minimumCoursePassMark", 16);
        when(mark1.getMarkValue()).thenReturn(10);
        when(mark2.getMarkValue()).thenReturn(20);
        when(markService.getMarksByStudentAndCourse(any(), any())).thenReturn(marks);
        when(courseMarkService.saveNonPassedCourseMark(anyDouble(), any(), any())).thenReturn(courseMark);

        // act
        FinishCourseResponse result = finishCourseService.finishCourse(request);

        // verify
        verify(markService).getMarksByStudentAndCourse(studentId, courseId);
        verify(courseMarkService).saveNonPassedCourseMark(15, studentId, courseId);
        assertThat(result.getCourseMark(), is(courseMark));
        assertThat(result.getMarks(), is(marks));

    }

}