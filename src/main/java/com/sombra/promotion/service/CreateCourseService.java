package com.sombra.promotion.service;

import com.sombra.promotion.dto.details.CourseInstructorLessonsDetails;
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
    public CourseInstructorLessonsDetails createCourse(CreateCourseRequest request) {
        return CourseInstructorLessonsDetails.builder()
                .instructorCourseDetails(instructorCourseService.createInstructorCourse(request.getCourse(), request.getInstructors()))
                .courseDetails(courseService.createCourse(request.getCourse()))
                .lessonDetails(lessonService.createLesson(new CreateLessonsRequest(request.getCourse(), request.getLessonNames())))
                .build();
    }

}
