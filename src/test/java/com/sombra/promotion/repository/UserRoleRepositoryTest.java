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

import static com.sombra.promotion.Constants.TEST_PASSWORD;
import static com.sombra.promotion.Constants.TEST_USERNAME;
import static com.sombra.promotion.enums.RoleEnum.instructor;
import static com.sombra.promotion.enums.RoleEnum.student;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@Import(TestHelpersConfiguration.class)
@ComponentScan(basePackageClasses = {
        UserRoleRepository.class,
        RoleRepository.class,
        UserRepository.class
})
class UserRoleRepositoryTest {

    @Autowired private UserRoleRepository repository;
    @Autowired private InsertUtils insert;
    @Autowired private SelectUtils select;

    @Nested
    @DisplayName("Set role for user")
    class SetRoleForUser {

        @Test
        void must_set_one_role_for_user() {

            // given
            insert.user(TEST_USERNAME, TEST_PASSWORD);

            // act
            repository.setRoleForUser(TEST_USERNAME, instructor);

            // verify
            RoleEnum result = select.roleTypeForUserWith(TEST_USERNAME);
            assertThat(result, is(instructor));

        }

        @Test
        void must_do_nothing_when_user_already_has_this_role() {

            // given
            UUID createdUserId = insert.user(TEST_USERNAME, TEST_PASSWORD);
            UUID instructorRoleId = select.roleId(instructor);
            insert.userRole(createdUserId, instructorRoleId);

            // act
            repository.setRoleForUser(TEST_USERNAME, instructor);

            // verify
            List<RoleEnum> result = select.roleTypesFor(TEST_USERNAME);
            assertThat(result, contains(is(instructor)));
        }

    }

    @Nested
    @DisplayName("Tests for user insert")
    class InsertUser {

        @Test
        void must_insert_user() {

            // act
            UUID userId = repository.insertUser("test-username1", "test-hashedPassword1", student);

            // verify
            User result = select.user(userId);
            assertThat(result, allOf(
                    hasProperty("username", is("test-username1")),
                    hasProperty("password", is("test-hashedPassword1")),
                    hasProperty("id", notNullValue())
            ));

        }

        @Test
        void must_insert_user_role() {

            // act
            UUID userId = repository.insertUser("test-username1", "test-hashedPassword1", student);

            // verify
            RoleEnum resultRoleStudentName = select.roleTypeForUserWith(userId);
            assertThat(resultRoleStudentName, is(student));

            UUID resultUserRoleUserId = select.userRoleForUserWith(userId);
            assertThat(resultUserRoleUserId, is(userId));

        }

    }

}
