package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.LessonsOfCourseAndStudentDetails;
import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class LessonsOfCourseAndStudentFactory {

    private final UserDetailsFactory userDetailsFactory;
    private final CourseDetailsFactory courseDetailsFactory;
    private final LessonDetailsFactory lessonDetailsFactory;

    public LessonsOfCourseAndStudentDetails build(
            String student,
            UUID courseId,
            List<Lesson> lessons
    ) {
        return LessonsOfCourseAndStudentDetails.builder()
                .lessons(lessons.stream()
                        .map(Lesson::getId)
                        .map(lessonDetailsFactory::build)
                        .collect(toList())
                ).student(userDetailsFactory.build(student))
                .course(courseDetailsFactory.build(courseId))
                .build();
    }

}
