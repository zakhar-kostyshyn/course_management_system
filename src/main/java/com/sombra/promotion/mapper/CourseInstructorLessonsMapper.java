package com.sombra.promotion.mapper;

import com.sombra.promotion.dto.response.CourseInstructorLessonsResponse;
import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.response.LessonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseInstructorLessonsMapper {

    public CourseInstructorLessonsResponse build(List<InstructorCourseResponse> listOfInstructorCourses,
                                                 CourseResponse course,
                                                 List<LessonResponse> lessons) {
        return CourseInstructorLessonsResponse.builder()
                .instructorsCourses(listOfInstructorCourses)
                .course(course)
                .lessons(lessons)
                .build();
    }

}
