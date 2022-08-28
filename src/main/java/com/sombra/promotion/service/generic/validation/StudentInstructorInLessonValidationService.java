package com.sombra.promotion.service.generic.validation;

import com.sombra.promotion.exception.LessonDontHaveStudentOrInstructorException;
import com.sombra.promotion.service.generic.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentInstructorInLessonValidationService {

    private final StudentInstructorInCourseValidationService studentInstructorInCourseValidationService;
    private final LessonService lessonService;

    @NonNull
    public void assertThatInstructorAndStudentHasLesson(UUID studentId, UUID instructorId, UUID lessonId) {
        try {
            UUID courseId = lessonService.findCourseIdByLessonId(lessonId);
            studentInstructorInCourseValidationService.assertThatInstructorAndStudentInCourse(
                    studentId, instructorId, courseId
            );
        } catch (Exception e) {
            throw new LessonDontHaveStudentOrInstructorException(studentId, instructorId, lessonId, e);
        }
    }

}
