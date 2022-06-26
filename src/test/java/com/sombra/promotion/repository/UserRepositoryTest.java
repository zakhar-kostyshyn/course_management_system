package com.sombra.promotion.repository;

import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.tables.pojos.User;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.enums.RoleEnum.student;
import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.Role.ROLE;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static com.sombra.promotion.tables.UserRole.USER_ROLE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@ComponentScan(basePackageClasses = {
        UserRepository.class
})
class UserRepositoryTest {

    @Autowired
    private DSLContext ctx;

    @Autowired
    private UserRepository repository;


    @Nested
    @DisplayName("Select user ID by username")
    class SelectUserIdByUsername {

        @Test
        void must_select_id_by_username() {

            // give
            UUID userId = UUID.randomUUID();
            String username = "test-username";

            ctx.insertInto(USER, USER.ID, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(userId, username, "test-password", UUID.randomUUID())
                    .execute();

            // act
            UUID actual = repository.selectUserIdByUsername(username);

            // verify
            assertThat(actual, is(userId));

        }

    }


    @Nested
    @DisplayName("Select role type by username")
    class SelectRoleTypeByUsername {

        @Test
        void must_select_roleType_by_username() {

            // setup
            String username = "test-username";
            RoleEnum roleType = student;

            UUID createdUserId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(username, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID instructorRoleId = ctx.select(ROLE.ID)
                    .from(ROLE)
                    .where(ROLE.NAME.eq(roleType))
                    .fetchAny()
                    .component1();

            ctx.insertInto(USER_ROLE, USER_ROLE.USER_ID, USER_ROLE.ROLE_ID, USER_ROLE.PREDEFINED)
                    .values(createdUserId, instructorRoleId, false)
                    .execute();

            // act
            RoleEnum actual = repository.selectRoleTypeByUsername(username);

            // verify
            assertThat(actual, is(roleType));

        }

    }


    @Nested
    @DisplayName("Select students by course id")
    class SelectStudentsByCourseId {

        @Test
        void must_select_users_by_course_id() {

            // given
            String givenStudentPrefix = "test-student-";
            String givenCourseName = "test-course";

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            List<UUID> studentIds = range(1, 5)
                    .mapToObj(i -> ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                            .values(givenStudentPrefix + i, "test-password", UUID.randomUUID())
                            .returningResult(USER.ID)
                            .fetchOne()
                            .component1()
                    ).collect(toList());

            studentIds.stream()
                    .limit(3)
                    .forEach(studentId -> ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                            .values(studentId, givenCourseId)
                            .execute()
                    );

            // act
            List<User> actual = repository.selectStudentsByCourseId(givenCourseId);

            // verify
            assertThat(actual, allOf(
                    hasItem(hasProperty("username", is("test-student-1"))),
                    hasItem(hasProperty("username", is("test-student-2"))),
                    hasItem(hasProperty("username", is("test-student-3"))),
                    not(hasItem(hasProperty("username", is("test-student-4"))))
            ));

        }

    }


    @Nested
    @DisplayName("Select all users")
    class SelectAllUsers {

        @Test
        void must_select_all_users() {

            // given
            String givenUser1 = "test-user-1";
            ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenUser1, "test-password", UUID.randomUUID())
                    .execute();

            String givenUser2 = "test-user-2";
            ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenUser2, "test-password", UUID.randomUUID())
                    .execute();

            String givenUser3 = "test-user-3";
            ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenUser3, "test-password", UUID.randomUUID())
                    .execute();

            // act
            List<User> actual = repository.selectAllUsers();

            // verify
            assertThat(actual, allOf(
                    hasItem(allOf(
                            hasProperty("username", is(givenUser1)),
                            hasProperty("password", is("test-password")),
                            hasProperty("salt", notNullValue())
                    )),
                    hasItem(allOf(
                            hasProperty("username", is(givenUser2)),
                            hasProperty("password", is("test-password")),
                            hasProperty("salt", notNullValue())
                    )),
                    hasItem(allOf(
                            hasProperty("username", is(givenUser3)),
                            hasProperty("password", is("test-password")),
                            hasProperty("salt", notNullValue())
                    ))
            ));

        }

    }

}
