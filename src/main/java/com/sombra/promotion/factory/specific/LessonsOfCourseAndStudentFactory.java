package com.sombra.promotion.factory.specific;

import com.sombra.promotion.dto.response.StudentCourseLessonsResponse;
import com.sombra.promotion.factory.generic.CourseFactory;
import com.sombra.promotion.factory.generic.LessonFactory;
import com.sombra.promotion.factory.generic.UserFactory;
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

    public StudentCourseLessonsResponse build(UUID studentId,
                                              UUID courseId,
                                              List<UUID> lessonIds) {
        return StudentCourseLessonsResponse.builder()
                .lessons(lessonIds.stream()
                        .map(lessonFactory::build)
                        .collect(toList())
                ).user(userFactory.build(studentId))
                .course(courseFactory.build(courseId))
                .build();
    }

}
