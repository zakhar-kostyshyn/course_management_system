package com.sombra.promotion.unit.service.generic;

import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.factory.generic.CourseFactory;
import com.sombra.promotion.repository.CourseRepository;
import com.sombra.promotion.service.generic.CourseService;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.tables.pojos.Course;
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
class CourseServiceTest {

    @Mock CourseRepository courseRepository;
    @Mock CourseFactory courseFactory;
    @Mock UUIDUtil uuidUtil;
    @InjectMocks
    CourseService courseService;
    @Captor ArgumentCaptor<Course> courseMarkCaptor;

    @Test
    void must_save_course_build_and_return_response() {

        // setup
        String courseName = "test-courseName";
        UUID courseId = UUID.randomUUID();
        CourseResponse response = mock(CourseResponse.class);
        when(uuidUtil.randomUUID()).thenReturn(courseId);
        when(courseFactory.build(any(Course.class))).thenReturn(response);

        // act
        CourseResponse result = courseService.saveCourse(courseName);

        // verify
        verify(uuidUtil).randomUUID();
        verify(courseRepository).persist(courseMarkCaptor.capture());
        verify(courseFactory).build(courseMarkCaptor.capture());
        assertThat(courseMarkCaptor.getValue().getId(), is(courseId));
        assertThat(courseMarkCaptor.getValue().getName(), is(courseName));
        assertThat(result, sameInstance(response));

    }

}
