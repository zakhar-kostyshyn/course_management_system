package com.sombra.promotion.unit.service.common.manyToMany;

import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.exception.NotFoundCourseBelongsForInstructorException;
import com.sombra.promotion.mapper.InstructorCourseMapper;
import com.sombra.promotion.repository.manyToMany.InstructorCourseRepository;
import com.sombra.promotion.service.common.manyToMany.InstructorCourseService;
import com.sombra.promotion.tables.pojos.InstructorCourse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorCourseServiceTest {

    @Mock InstructorCourseRepository instructorCourseRepository;
    @Mock
    InstructorCourseMapper instructorCourseMapper;
    @InjectMocks
    InstructorCourseService instructorCourseService;
    @InjectMocks @Spy InstructorCourseService spy;
    @Captor ArgumentCaptor<InstructorCourse> instructorCourseCaptor;

    UUID instructorId;
    UUID courseId;
    InstructorCourseResponse response;

    @BeforeEach
    void setUp() {
        instructorId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        response = mock(InstructorCourseResponse.class);
    }

    @Test
    void must_save_instructor_course() {
        instructorCourseService.saveInstructorCourse(instructorId, courseId);
        verify(instructorCourseRepository).persist(instructorCourseCaptor.capture());
        assertThat(instructorCourseCaptor.getValue().getInstructorId(), is(instructorId));
        assertThat(instructorCourseCaptor.getValue().getCourseId(), is(courseId));
    }

    @Test
    void must_return_response_of_saved_instructor_course() {
        when(instructorCourseMapper.build(any(InstructorCourse.class))).thenReturn(response);
        var result = instructorCourseService.saveInstructorCourse(instructorId, courseId);
        assertThat(result, sameInstance(response));
    }

    @Test
    void must_save_list_of_instructor_course() {
        spy.saveInstructorCourse(List.of(instructorId, instructorId), courseId);
        verify(spy, times(2)).saveInstructorCourse(instructorId, courseId);
    }

    @Test
    void must_return_list_of_instructor_course_response() {
        doReturn(response).when(spy).saveInstructorCourse(any(UUID.class), any());
        var result = spy.saveInstructorCourse(List.of(instructorId, instructorId), courseId);
        assertThat(result.size(), is(2));
        assertThat(result, everyItem(sameInstance(response)));
    }

    @Test
    void must_throw_exception_when_instructor_course_not_exist() {
        when(instructorCourseRepository.exist(any(), any())).thenReturn(false);
        assertThrows(
                NotFoundCourseBelongsForInstructorException.class,
                () -> instructorCourseService.assertThatInstructorInCourse(instructorId, courseId)
        );
    }

}
