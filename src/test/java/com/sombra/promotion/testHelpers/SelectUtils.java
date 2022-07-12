package com.sombra.promotion.testHelpers;

import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.tables.pojos.*;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.Feedback.FEEDBACK;
import static com.sombra.promotion.tables.Homework.HOMEWORK;
import static com.sombra.promotion.tables.InstructorCourse.INSTRUCTOR_COURSE;
import static com.sombra.promotion.tables.Lesson.LESSON;
import static com.sombra.promotion.tables.Mark.MARK;
import static com.sombra.promotion.tables.Role.ROLE;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static com.sombra.promotion.tables.UserRole.USER_ROLE;

public class SelectUtils {

    private final DSLContext ctx;

    public SelectUtils(DSLContext ctx) {
        this.ctx = ctx;
    }

    public Feedback feedback() {
        return ctx.select().from(FEEDBACK).fetchSingleInto(Feedback.class);
    }

    public Homework homework() {
        return ctx.select().from(HOMEWORK).fetchAnyInto(Homework.class);
    }

    public InstructorCourse instructorCourse() {
        return ctx.select().from(INSTRUCTOR_COURSE).fetchSingleInto(InstructorCourse.class);
    }

    public Course course() {
        return ctx.select().from(COURSE).fetchAnyInto(Course.class);
    }

    public Lesson lesson() {
        return ctx.select().from(LESSON).fetchAnyInto(Lesson.class);
    }

    public Mark mark() {
        return ctx.select().from(MARK).fetchSingleInto(Mark.class);
    }

    public StudentCourse studentCourse() {
        return ctx.select().from(STUDENT_COURSE).fetchAnyInto(StudentCourse.class);
    }

    public User user(UUID id) {
        return ctx.select().from(USER).where(USER.ID.eq(id)).fetchSingle().into(User.class);
    }

    public UUID roleId(RoleEnum type) {
        return ctx.select(ROLE.ID).from(ROLE).where(ROLE.NAME.eq(type)).fetchAny().component1();
    }

    public RoleEnum roleTypeForUserWith(String username) {
        return ctx.select().from(USER).join(USER_ROLE).on(USER_ROLE.USER_ID.eq(USER.ID)).join(ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID)).where(USER.USERNAME.eq(username)).fetchAny(ROLE.NAME);
    }

    public List<RoleEnum> roleTypesFor(String username) {
        return ctx.select().from(USER).join(USER_ROLE).on(USER_ROLE.USER_ID.eq(USER.ID)).join(ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID)).where(USER.USERNAME.eq(username)).fetch(ROLE.NAME);
    }

    public RoleEnum roleTypeForUserWith(UUID userId) {
        return ctx.select().from(USER_ROLE).join(ROLE).on(USER_ROLE.ROLE_ID.eq(ROLE.ID)).where(USER_ROLE.USER_ID.eq(userId)).fetchSingle().into(ROLE.NAME).component1();
    }

    public UUID userRoleForUserWith(UUID userId) {
        return ctx.select().from(USER_ROLE).join(ROLE).on(USER_ROLE.ROLE_ID.eq(ROLE.ID)).where(USER_ROLE.USER_ID.eq(userId)).fetchSingle().into(USER_ROLE.USER_ID).component1();
    }
}
