package com.sombra.promotion.repository;

import com.sombra.promotion.testConfigs.TestHelpersConfiguration;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.tables.pojos.User;
import com.sombra.promotion.testHelpers.InsertUtils;
import com.sombra.promotion.testHelpers.SelectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.Constants.*;
import static com.sombra.promotion.enums.RoleEnum.student;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@Import(TestHelpersConfiguration.class)
@ComponentScan(basePackageClasses = {
        UserRepository.class
})
class UserRepositoryTest {

    @Autowired private UserRepository repository;
    @Autowired private InsertUtils insert;
    @Autowired private SelectUtils select;

    @Nested
    @DisplayName("Select user ID by username")
    class SelectUserIdByUsername {

        @Test
        void must_select_id_by_username() {

            // give
            UUID userId = UUID.randomUUID();
            insert.user(userId, TEST_USERNAME, TEST_PASSWORD);

            // act
            UUID result = repository.selectUserIdByUsername(TEST_USERNAME);

            // verify
            assertThat(result, is(userId));

        }

    }


    @Nested
    @DisplayName("Select role type by username")
    class SelectRoleTypeByUsername {

        @Test
        void must_select_roleType_by_username() {

            // setup
            RoleEnum roleType = student;
            UUID createdUserId = insert.user(TEST_USERNAME, TEST_PASSWORD);
            UUID instructorRoleId = select.roleId(roleType);
            insert.userRole(createdUserId, instructorRoleId);

            // act
            RoleEnum result = repository.selectRoleTypeByUsername(TEST_USERNAME);

            // verify
            assertThat(result, is(roleType));

        }

    }


    @Nested
    @DisplayName("Select students by course id")
    class SelectStudentsByCourseId {

        @Test
        void must_select_users_by_course_id() {

            // given
            UUID givenCourseId = insert.course(TEST_COURSE);
            range(1, 5)
                    .mapToObj(i -> insert.user(TEST_STUDENT + "-" + i, TEST_PASSWORD))
                    .limit(3)
                    .forEach(studentId -> insert.studentCourse(studentId, givenCourseId));

            // act
            List<User> result = repository.selectStudentsByCourseId(givenCourseId);

            // verify
            assertThat(result, allOf(
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
            insert.user(givenUser1, TEST_PASSWORD);

            String givenUser2 = "test-user-2";
            insert.user(givenUser2, TEST_PASSWORD);

            String givenUser3 = "test-user-3";
            insert.user(givenUser3, TEST_PASSWORD);

            // act
            List<User> result = repository.selectAllUsers();

            // verify
            assertThat(result, allOf(
                    hasItem(allOf(
                            hasProperty("username", is(givenUser1)),
                            hasProperty("password", is("test-password"))
                    )),
                    hasItem(allOf(
                            hasProperty("username", is(givenUser2)),
                            hasProperty("password", is("test-password"))
                    )),
                    hasItem(allOf(
                            hasProperty("username", is(givenUser3)),
                            hasProperty("password", is("test-password"))
                    ))
            ));

        }

    }

    @Nested
    class SelectUserByUsername {

        @Test
        void must_select_and_return_user_by_username() {
            UUID userId = insert.user(TEST_USERNAME, TEST_PASSWORD);
            User user = repository.selectUserByUsername(TEST_USERNAME);
            assertThat(user, allOf(
                    hasProperty("id", is(userId)),
                    hasProperty("username", is(TEST_USERNAME)),
                    hasProperty("password", is(TEST_PASSWORD))
            ));
        }

    }


    @Nested
    class SelectUserById {

        @Test
        void must_select_and_return_user_by_id() {
            UUID userId = insert.user(TEST_USERNAME, TEST_PASSWORD);
            User user = repository.selectUserById(userId);
            assertThat(user, allOf(
                    hasProperty("id", is(userId)),
                    hasProperty("username", is(TEST_USERNAME)),
                    hasProperty("password", is(TEST_PASSWORD))
            ));
        }

    }

}
