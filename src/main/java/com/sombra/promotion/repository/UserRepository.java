package com.sombra.promotion.repository;

import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.Role.ROLE;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static com.sombra.promotion.tables.UserRole.USER_ROLE;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {

    private final DSLContext ctx;

    public UUID selectUserIdByUsername(String username) {
        return requireNonNull(ctx.select(USER.ID)
                .from(USER)
                .where(USER.USERNAME.eq(username))
                .fetchOne()
        ).component1();
    }

    public RoleEnum selectRoleTypeByUsername(String username) {
        return requireNonNull(ctx.select(ROLE.NAME)
                .from(USER)
                .join(USER_ROLE).on(USER.ID.eq(USER_ROLE.USER_ID))
                .join(ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                .where(USER.USERNAME.eq(username))
                .fetchOne()
        ).component1();
    }

    public List<User> selectStudentsByCourseId(UUID courseId) {
        return ctx.select(USER.ID, USER.USERNAME, USER.PASSWORD, USER.SALT)
                .from(COURSE)
                .join(STUDENT_COURSE).on(STUDENT_COURSE.COURSE_ID.eq(COURSE.ID))
                .join(USER).on(USER.ID.eq(STUDENT_COURSE.STUDENT_ID))
                .where(COURSE.ID.eq(courseId))
                .fetchInto(User.class);
    }

    public List<User> selectAllUsers() {
        return ctx.select()
                .from(USER)
                .fetchInto(User.class);
    }

}
