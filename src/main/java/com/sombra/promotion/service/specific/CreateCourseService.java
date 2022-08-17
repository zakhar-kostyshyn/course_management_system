package com.sombra.promotion.service.specific;

import com.sombra.promotion.dto.response.CourseInstructorLessonsResponse;
import com.sombra.promotion.dto.request.CreateCourseRequest;
import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.factory.specific.CourseInstructorLessonsFactory;
import com.sombra.promotion.service.generic.CourseService;
import com.sombra.promotion.service.generic.LessonService;
import com.sombra.promotion.service.generic.manyToMany.InstructorCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CreateCourseService {

    private final CourseService courseService;
    private final InstructorCourseService instructorCourseService;
    private final LessonService lessonService;
    private final CourseInstructorLessonsFactory courseInstructorLessonsFactory;

    public CourseInstructorLessonsResponse createCourse(CreateCourseRequest request) {
        CourseResponse course = courseService.saveCourse(request.getCourseName());
        List<InstructorCourseResponse> instructors = instructorCourseService.saveInstructorCourse(course.getId(), request.getInstructorIds());
        List<LessonResponse> lessons = request.getLessons().stream()
                .map(lessonName -> lessonService.saveLesson(lessonName, course.getId()))
                .collect(toList());
        return courseInstructorLessonsFactory.build(instructors, course, lessons);
    }

}
