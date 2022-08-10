package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.CourseInstructorLessonsResponse;
import com.sombra.promotion.dto.request.CreateCourseRequest;
import com.sombra.promotion.dto.request.CreateLessonsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCourseService {

    private final CourseService courseService;
    private final InstructorCourseService instructorCourseService;
    private final LessonService lessonService;

    @Transactional
    public CourseInstructorLessonsResponse createCourse(CreateCourseRequest request) {
        return CourseInstructorLessonsResponse.builder()
                .instructorCourseDetails(instructorCourseService.createInstructorCourse(request.getCourse(), request.getInstructors()))
                .courseResponse(courseService.createCourse(request.getCourse()))
                .lessonDetails(lessonService.createLesson(new CreateLessonsRequest(request.getCourse(), request.getLessonNames())))
                .build();
    }

}
