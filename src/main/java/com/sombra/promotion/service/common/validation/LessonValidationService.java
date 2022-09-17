package com.sombra.promotion.service.common.validation;

import com.sombra.promotion.exception.InstructorNotAbleToCreateLesson;
import com.sombra.promotion.exception.NotFoundCourseBelongsForInstructorException;
import com.sombra.promotion.service.common.manyToMany.InstructorCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonValidationService {

    private final InstructorCourseService instructorCourseService;

    @NonNull
    public void assertThatInstructorInCourse(UUID instructorId, UUID courseId) {
        try {
            instructorCourseService.assertThatInstructorInCourse(instructorId, courseId);
        } catch (NotFoundCourseBelongsForInstructorException e) {
            throw new InstructorNotAbleToCreateLesson(instructorId, courseId, e);
        }
    }

}
