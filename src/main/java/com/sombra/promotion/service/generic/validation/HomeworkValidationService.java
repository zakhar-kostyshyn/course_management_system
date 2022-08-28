package com.sombra.promotion.service.generic.validation;

import com.sombra.promotion.exception.NotFoundCourseBelongsForStudentException;
import com.sombra.promotion.exception.StudentNotAbleToUploadHomework;
import com.sombra.promotion.service.generic.LessonService;
import com.sombra.promotion.service.generic.manyToMany.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeworkValidationService {

    private final StudentCourseService studentCourseService;
    private final LessonService lessonService;

    @NonNull
    public void assertThatInstructorInCourse(UUID studentId, UUID lessonId) {
        UUID courseId = null;
        try {
            courseId = lessonService.findCourseIdByLessonId(lessonId);
            studentCourseService.assertThatStudentInCourse(studentId, courseId);
        } catch (NotFoundCourseBelongsForStudentException e) {
            throw new StudentNotAbleToUploadHomework(studentId, courseId, lessonId, e);
        }
    }

}
