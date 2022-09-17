package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.CourseInstructorLessonsResponse;
import com.sombra.promotion.dto.request.CreateCourseRequest;
import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.exception.NotEnoughLessonsForCourseException;
import com.sombra.promotion.mapper.CourseInstructorLessonsMapper;
import com.sombra.promotion.service.common.CourseService;
import com.sombra.promotion.service.common.LessonService;
import com.sombra.promotion.service.common.manyToMany.InstructorCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CreateCourseService {

    private final CourseService courseService;
    private final InstructorCourseService instructorCourseService;
    private final LessonService lessonService;
    private final CourseInstructorLessonsMapper courseInstructorLessonsMapper;

    @Value("${app.minimum-lessons-per-course}")
    private int minimumLessonsPerCourse;

    @Transactional
    public CourseInstructorLessonsResponse createCourse(CreateCourseRequest request) {
        if (request.getLessonNames().size() < minimumLessonsPerCourse)
            throw new NotEnoughLessonsForCourseException(request.getLessonNames().size(), minimumLessonsPerCourse);
        CourseResponse course = courseService.saveCourse(request.getCourseName());
        List<InstructorCourseResponse> listOfInstructorCourse = instructorCourseService.saveInstructorCourse(request.getInstructorIds(), course.getCourseId());
        List<LessonResponse> lessons = request.getLessonNames().stream()
                .map(lessonName -> lessonService.addLessonToCourse(lessonName, course.getCourseId()))
                .collect(toList());
        return courseInstructorLessonsMapper.build(listOfInstructorCourse, course, lessons);
    }

}
