package com.sombra.promotion.service;

import com.sombra.promotion.dto.details.LessonsOfCourseAndStudentDetails;
import com.sombra.promotion.factory.LessonsOfCourseAndStudentFactory;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentLessonCourseService {

    private final LessonsOfCourseAndStudentFactory lessonsOfCourseAndStudentFactory;
    private final LessonRepository lessonRepository;
    private final StudentCourseService studentCourseService;


    public LessonsOfCourseAndStudentDetails getStudentLessonsFromCourse(UUID courseId, SecurityUser authenticatedUser) {
        String student = authenticatedUser.getUsername();
        studentCourseService.assertCourseContainsStudent(courseId, student);
        return lessonsOfCourseAndStudentFactory.build(
            student, courseId, lessonRepository.selectLessonsByCourseId(courseId)
        );
    }

}
