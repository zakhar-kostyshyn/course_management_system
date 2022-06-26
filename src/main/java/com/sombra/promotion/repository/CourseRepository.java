package com.sombra.promotion.repository;

import com.sombra.promotion.tables.pojos.Course;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.InstructorCourse.INSTRUCTOR_COURSE;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class CourseRepository {

    private final DSLContext ctx;

    public UUID selectCourseIdByName(String courseName) {
        return requireNonNull(ctx.select(COURSE.ID)
                .from(COURSE)
                .where(COURSE.NAME.eq(courseName))
                .fetchOne()
        ).component1();
    }

    public List<Course> selectCoursesByStudent(String student) {
        return ctx.select(COURSE.ID, COURSE.NAME)
                .from(COURSE)
                .join(STUDENT_COURSE).on(STUDENT_COURSE.COURSE_ID.eq(COURSE.ID))
                .join(USER).on(STUDENT_COURSE.STUDENT_ID.eq(USER.ID))
                .where(USER.USERNAME.eq(student))
                .fetchInto(Course.class);
    }

    public List<Course> selectCoursesByInstructor(String instructor) {
        return ctx.select(COURSE.ID, COURSE.NAME)
                .from(COURSE)
                .join(INSTRUCTOR_COURSE).on(INSTRUCTOR_COURSE.COURSE_ID.eq(COURSE.ID))
                .join(USER).on(USER.ID.eq(INSTRUCTOR_COURSE.INSTRUCTOR_ID))
                .where(USER.USERNAME.eq(instructor))
                .fetchInto(Course.class);
    }

}
