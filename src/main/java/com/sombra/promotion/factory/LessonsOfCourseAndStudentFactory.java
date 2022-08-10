package com.sombra.promotion.factory;

import com.sombra.promotion.dto.response.LessonsOfCourseAndStudentResponse;
import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class LessonsOfCourseAndStudentFactory {

    private final UserFactory userFactory;
    private final CourseFactory courseFactory;
    private final LessonFactory lessonFactory;

    public LessonsOfCourseAndStudentResponse build(
            String student,
            UUID courseId,
            List<Lesson> lessons
    ) {
        return LessonsOfCourseAndStudentResponse.builder()
                .lessons(lessons.stream()
                        .map(Lesson::getId)
                        .map(lessonFactory::build)
                        .collect(toList())
                ).student(userFactory.build(student))
                .course(courseFactory.build(courseId))
                .build();
    }

}
