package com.sombra.promotion.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.CourseMark.COURSE_MARK;

@Repository
@RequiredArgsConstructor
public class CourseMarkRepository {

    private final DSLContext ctx;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    // TODO add pass or not value
    public UUID insertCourseMark(int mark, String student, String course, boolean isCoursePassed) {
        UUID courseId = courseRepository.selectCourseIdBy(student);
        UUID studentId = userRepository.selectUserIdByUsername(course);
        return ctx.insertInto(COURSE_MARK, COURSE_MARK.MARK, COURSE_MARK.STUDENT_ID, COURSE_MARK.COURSE_ID)
                .values(mark, studentId, courseId)
                .returningResult(COURSE_MARK.ID)
                .fetchSingle()
                .component1();
    }


}
