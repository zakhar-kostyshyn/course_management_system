package com.sombra.promotion.service.generic;

import com.sombra.promotion.dto.request.SaveMarkRequest;
import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.dto.response.MarkResponse;
import com.sombra.promotion.factory.generic.MarkFactory;
import com.sombra.promotion.repository.MarkRepository;
import com.sombra.promotion.service.generic.validation.StudentInstructorInLessonValidationService;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.tables.pojos.Mark;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MarkServiceTest {

    @Mock MarkRepository markRepository;
    @Mock MarkFactory markFactory;
    @Mock LessonService lessonService;
    @Mock UUIDUtil uuidUtil;
    @Mock StudentInstructorInLessonValidationService studentInstructorInLessonValidationService;
    @InjectMocks MarkService markService;
    @InjectMocks @Spy MarkService spy;
    @Captor ArgumentCaptor<Mark> markCaptor;

    @Test
    void must_validate_and_save_mark_then_build_and_return_response() {

        // setup
        int mark = 10;
        UUID studentId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();
        UUID markId = UUID.randomUUID();
        MarkResponse response = mock(MarkResponse.class);
        when(uuidUtil.randomUUID()).thenReturn(markId);
        when(markFactory.build(any(Mark.class))).thenReturn(response);

        // act
        MarkResponse result = markService.saveMark(new SaveMarkRequest(mark, studentId, lessonId, instructorId));

        // verify
        verify(studentInstructorInLessonValidationService).assertThatInstructorAndStudentHasLesson(studentId, instructorId, lessonId);
        verify(uuidUtil).randomUUID();
        verify(markRepository).persist(markCaptor.capture());
        verify(markFactory).build(markCaptor.capture());
        assertThat(markCaptor.getValue().getId(), is(markId));
        assertThat(markCaptor.getValue().getMark(), is(mark));
        assertThat(markCaptor.getValue().getInstructorId(), is(instructorId));
        assertThat(markCaptor.getValue().getStudentId(), is(studentId));
        assertThat(markCaptor.getValue().getLessonId(), is(lessonId));
        assertThat(result, sameInstance(response));

    }

    @Test
    void must_get_marks_by_student_and_lessons() {

        // setup
        UUID studentId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        Mark mark = mock(Mark.class);
        MarkResponse response = mock(MarkResponse.class);
        when(markRepository.findByStudentIdAndLessonId(any(), any())).thenReturn(List.of(mark));
        when(markFactory.build(any(List.class))).thenReturn(List.of(response));

        // act
        List<MarkResponse> result = markService.getMarksByStudentAndHisLessons(studentId, List.of(lessonId));

        // verify
        verify(markRepository).findByStudentIdAndLessonId(studentId, List.of(lessonId));
        verify(markFactory).build(List.of(mark));
        assertThat(result, hasItems(sameInstance(response)));

    }


    @Test
    void must_get_marks_by_student_and_course() {

        // setup
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        LessonResponse lessonResponse = mock(LessonResponse.class);
        MarkResponse response = mock(MarkResponse.class);
        when(lessonResponse.getLessonId()).thenReturn(lessonId);
        when(lessonService.getLessonsByCourse(any())).thenReturn(List.of(lessonResponse));
        when(spy.getMarksByStudentAndHisLessons(any(), any())).thenReturn(List.of(response));

        // act
        List<MarkResponse> result = spy.getMarksByStudentAndCourse(studentId, courseId);

        // verify
        verify(lessonService).getLessonsByCourse(courseId);
        verify(spy).getMarksByStudentAndHisLessons(studentId, List.of(lessonId));
        assertThat(result, hasItems(sameInstance(response)));

    }

}