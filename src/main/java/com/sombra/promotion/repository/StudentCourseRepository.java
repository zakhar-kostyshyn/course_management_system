package com.sombra.promotion.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;

@Repository
@RequiredArgsConstructor
public class StudentCourseRepository {

    private final DSLContext ctx;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public void setStudentForCourse(String studentUsername, String courseName) {
        UUID studentId = userRepository.selectUserIdByUsername(studentUsername);
        UUID courseId = courseRepository.selectCourseIdByName(courseName);

        ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                .values(studentId, courseId)
                .execute();
    }

}
