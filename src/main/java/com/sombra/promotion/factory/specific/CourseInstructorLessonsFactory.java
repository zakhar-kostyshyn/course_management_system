package com.sombra.promotion.factory.specific;

import com.sombra.promotion.dto.response.CourseInstructorLessonsResponse;
import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.response.LessonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseInstructorLessonsFactory {

    public CourseInstructorLessonsResponse build(List<InstructorCourseResponse> instructors,
                                                 CourseResponse course,
                                                 List<LessonResponse> lessons) {
        return CourseInstructorLessonsResponse.builder()
                .instructorsCourses(instructors)
                .course(course)
                .lessons(lessons)
                .build();
    }

}
