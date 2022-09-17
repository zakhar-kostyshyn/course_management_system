package com.sombra.promotion.unit.service.generic;

import com.sombra.promotion.dto.request.CreateLessonsRequest;
import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.mapper.common.LessonMapper;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.service.common.LessonService;
import com.sombra.promotion.service.common.validation.LessonValidationService;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.tables.pojos.Lesson;
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
class LessonServiceTest {

    @Mock LessonRepository lessonRepository;
    @Mock
    LessonMapper lessonMapper;
    @Mock UUIDUtil uuidUtil;
    @Mock LessonValidationService lessonValidationService;
    @InjectMocks
    LessonService lessonService;
    @InjectMocks @Spy LessonService spy;
    @Captor ArgumentCaptor<Lesson> lessonCaptor;

    @Test
    void must_create_list_of_lessons_add_them_for_course_and_instructor_and_then_return_built_response() {

        // setup
        List<String> lessons = List.of("test-lesson");
        UUID courseId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        CreateLessonsRequest request = new CreateLessonsRequest(courseId, lessons, instructorId);
        LessonResponse response = mock(LessonResponse.class);
        doReturn(response).when(spy).addLessonToCourse(anyString(), any());

        // act
        List<LessonResponse> result = spy.saveLessons(request);

        // verify
        verify(lessonValidationService).assertThatInstructorInCourse(instructorId, courseId);
        verify(spy).addLessonToCourse("test-lesson", courseId);
        assertThat(result, hasItems(sameInstance(response)));

    }


    @Test
    void must_add_lesson_to_course() {

        // setup
        String lesson = "test-lesson";
        UUID courseId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        LessonResponse response = mock(LessonResponse.class);
        when(uuidUtil.randomUUID()).thenReturn(lessonId);
        when(lessonMapper.build(any(Lesson.class))).thenReturn(response);

        // act
        LessonResponse result = lessonService.addLessonToCourse(lesson, courseId);

        // verify
        verify(uuidUtil).randomUUID();
        verify(lessonRepository).persist(lessonCaptor.capture());
        verify(lessonMapper).build(lessonCaptor.capture());
        assertThat(lessonCaptor.getValue().getId(), is(lessonId));
        assertThat(lessonCaptor.getValue().getName(), is(lesson));
        assertThat(lessonCaptor.getValue().getCourseId(), is(courseId));
        assertThat(result, sameInstance(response));

    }

}
