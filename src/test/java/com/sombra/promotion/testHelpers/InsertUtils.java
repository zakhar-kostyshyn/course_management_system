package com.sombra.promotion.testHelpers;

import org.jooq.DSLContext;

import java.util.UUID;

import static com.sombra.promotion.Tables.INSTRUCTOR_COURSE;
import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.Lesson.LESSON;
import static com.sombra.promotion.tables.Mark.MARK;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static com.sombra.promotion.tables.UserRole.USER_ROLE;

public class InsertUtils {

    private DSLContext ctx;
    public InsertUtils(DSLContext ctx) {
        this.ctx = ctx;
    }

    public UUID user(String username, String password) {
        return ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                .values(username, password, UUID.randomUUID())
                .returningResult(USER.ID)
                .fetchOne()
                .component1();
    }

    public UUID user(UUID id, String username, String password) {
        return ctx.insertInto(USER, USER.ID, USER.USERNAME, USER.PASSWORD, USER.SALT)
                .values(id, username, password, UUID.randomUUID())
                .returningResult(USER.ID)
                .fetchOne()
                .component1();
    }

    public UUID course(String course) {
        return ctx.insertInto(COURSE, COURSE.NAME)
                .values(course)
                .returning(COURSE.ID)
                .fetchOne()
                .component1();
    }

    public UUID lesson(String lesson, UUID courseId) {
        return ctx.insertInto(LESSON, LESSON.NAME, LESSON.COURSE_ID)
                .values(lesson, courseId)
                .returning(LESSON.ID)
                .fetchOne()
                .component1();
    }

    public UUID mark(int mark, UUID studentId, UUID instructorId, UUID lessonId) {
        return ctx.insertInto(MARK, MARK.STUDENT_ID, MARK.INSTRUCTOR_ID, MARK.LESSON_ID, MARK.MARK_)
                .values(studentId, instructorId, lessonId, mark)
                .returning(MARK.ID)
                .fetchOne()
                .component1();
    }

    public void userRole(UUID userId, UUID roleId) {
        ctx.insertInto(USER_ROLE, USER_ROLE.USER_ID, USER_ROLE.ROLE_ID, USER_ROLE.PREDEFINED)
                .values(userId, roleId, false)
                .execute();
    }

    public void studentCourse(UUID userId, UUID courseId) {
        ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                .values(userId, courseId)
                .execute();
    }

    public void instructorCourse(UUID userId, UUID courseId) {
        ctx.insertInto(INSTRUCTOR_COURSE, INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                .values(userId, courseId)
                .execute();
    }

}
