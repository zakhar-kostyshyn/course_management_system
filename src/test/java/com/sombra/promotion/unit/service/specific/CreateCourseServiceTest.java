package com.sombra.promotion.unit.service.specific;

import com.sombra.promotion.dto.request.CreateCourseRequest;
import com.sombra.promotion.dto.response.CourseInstructorLessonsResponse;
import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.exception.NotEnoughLessonsForCourseException;
import com.sombra.promotion.mapper.CourseInstructorLessonsMapper;
import com.sombra.promotion.service.common.CourseService;
import com.sombra.promotion.service.common.LessonService;
import com.sombra.promotion.service.common.manyToMany.InstructorCourseService;
import com.sombra.promotion.service.CreateCourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCourseServiceTest {

    @Mock CourseService courseService;
    @Mock InstructorCourseService instructorCourseService;
    @Mock LessonService lessonService;
    @Mock
    CourseInstructorLessonsMapper courseInstructorLessonsMapper;
    @InjectMocks
    CreateCourseService createCourseService;

    @Test
    void must_throw_exception_when_lessons_less_then_minimum_lesson_per_course_value() {

        // setup
        ReflectionTestUtils.setField(createCourseService, "minimumLessonsPerCourse", 5);
        UUID instructorId = UUID.randomUUID();
        String courseName = "test-courseName";
        List<String> lessonNames = List.of("test-lesson");
        CreateCourseRequest request = new CreateCourseRequest(List.of(instructorId), courseName, lessonNames);

        // act
        // verify
        assertThrows(NotEnoughLessonsForCourseException.class, () -> createCourseService.createCourse(request));

    }

    @Test
    void must_save_course_get_instructors_and_save_manyToMany_these_instructors_with_created_course() {

        // setup
        ReflectionTestUtils.setField(createCourseService, "minimumLessonsPerCourse", 1);
        List<UUID> instructorIds = List.of(UUID.randomUUID());
        String courseName = "test-courseName";
        List<String> lessonNames = List.of("test-lesson");
        CreateCourseRequest request = new CreateCourseRequest(instructorIds, courseName, lessonNames);
        UUID courseId = UUID.randomUUID();
        CourseResponse courseResponse = new CourseResponse(courseId, courseName);
        List<InstructorCourseResponse> instructorCourseResponses = List.of(mock(InstructorCourseResponse.class));
        LessonResponse lesson = mock(LessonResponse.class);
        when(courseService.saveCourse(anyString())).thenReturn(courseResponse);
        when(instructorCourseService.saveInstructorCourse(anyList(), any())).thenReturn(instructorCourseResponses);
        when(lessonService.addLessonToCourse(anyString(), any())).thenReturn(lesson);

        // act
        createCourseService.createCourse(request);

        // verify
        verify(courseService).saveCourse(courseName);
        verify(instructorCourseService).saveInstructorCourse(instructorIds, courseId);
        verify(lessonService).addLessonToCourse("test-lesson", courseId);
        verify(courseInstructorLessonsMapper).build(instructorCourseResponses, courseResponse, List.of(lesson));

    }

    @Test
    void must_after_course_creation_process_return_built_response() {

        ReflectionTestUtils.setField(createCourseService, "minimumLessonsPerCourse", 1);
        List<UUID> instructorIds = List.of(UUID.randomUUID());
        String courseName = "test-courseName";
        List<String> lessonNames = List.of("test-lesson");
        CreateCourseRequest request = new CreateCourseRequest(instructorIds, courseName, lessonNames);
        UUID courseId = UUID.randomUUID();
        CourseResponse courseResponse = new CourseResponse(courseId, courseName);
        CourseInstructorLessonsResponse response = mock(CourseInstructorLessonsResponse.class);
        when(courseService.saveCourse(anyString())).thenReturn(courseResponse);
        when(courseInstructorLessonsMapper.build(anyList(), any(), anyList())).thenReturn(response);

        // act
        CourseInstructorLessonsResponse result = createCourseService.createCourse(request);

        // verify
        assertThat(result, sameInstance(response));

    }

}
