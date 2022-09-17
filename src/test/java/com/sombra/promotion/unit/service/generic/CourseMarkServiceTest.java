package com.sombra.promotion.unit.service.generic;

import com.sombra.promotion.dto.response.CourseMarkResponse;
import com.sombra.promotion.mapper.common.CourseMarkMapper;
import com.sombra.promotion.repository.CourseMarkRepository;
import com.sombra.promotion.service.common.CourseMarkService;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.tables.pojos.CourseMark;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseMarkServiceTest {

    @Mock CourseMarkRepository courseMarkRepository;
    @Mock UUIDUtil uuidUtil;
    @Mock
    CourseMarkMapper courseMarkMapper;
    @InjectMocks
    CourseMarkService courseMarkService;
    @Captor ArgumentCaptor<CourseMark> courseMarkCaptor;

    @Test
    void must_save_passed_course_mark_and_return_built_response() {

        // setup
        double mark = 1D;
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID courseMarkID = UUID.randomUUID();
        CourseMarkResponse response = mock(CourseMarkResponse.class);
        when(uuidUtil.randomUUID()).thenReturn(courseMarkID);
        when(courseMarkMapper.build(any(CourseMark.class))).thenReturn(response);

        // act
        CourseMarkResponse result = courseMarkService.savePassedCourseMark(mark, studentId, courseId);

        // verify
        verify(uuidUtil).randomUUID();
        verify(courseMarkRepository).persist(courseMarkCaptor.capture());
        verify(courseMarkMapper).build(courseMarkCaptor.capture());
        assertThat(courseMarkCaptor.getValue().getId(), is(courseMarkID));
        assertThat(courseMarkCaptor.getValue().getMark(), is(mark));
        assertThat(courseMarkCaptor.getValue().getStudentId(), is(studentId));
        assertThat(courseMarkCaptor.getValue().getCourseId(), is(courseId));
        assertThat(courseMarkCaptor.getValue().getCoursePassed(), is(true));
        assertThat(result, sameInstance(response));

    }


    @Test
    void must_save_nonPassed_course_mark_and_return_built_response() {

        // setup
        double mark = 1.0;
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID courseMarkID = UUID.randomUUID();
        CourseMarkResponse response = mock(CourseMarkResponse.class);
        when(uuidUtil.randomUUID()).thenReturn(courseMarkID);
        when(courseMarkMapper.build(any(CourseMark.class))).thenReturn(response);

        // act
        CourseMarkResponse result = courseMarkService.saveNonPassedCourseMark(mark, studentId, courseId);

        // verify
        verify(uuidUtil).randomUUID();
        verify(courseMarkRepository).persist(courseMarkCaptor.capture());
        verify(courseMarkMapper).build(courseMarkCaptor.capture());
        assertThat(courseMarkCaptor.getValue().getId(), is(courseMarkID));
        assertThat(courseMarkCaptor.getValue().getMark(), is(mark));
        assertThat(courseMarkCaptor.getValue().getStudentId(), is(studentId));
        assertThat(courseMarkCaptor.getValue().getCourseId(), is(courseId));
        assertThat(courseMarkCaptor.getValue().getCoursePassed(), is(false));
        assertThat(result, sameInstance(response));

    }

}
