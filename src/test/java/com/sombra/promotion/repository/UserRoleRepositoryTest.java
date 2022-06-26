package com.sombra.promotion.repository;

import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.tables.pojos.User;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.enums.RoleEnum.instructor;
import static com.sombra.promotion.enums.RoleEnum.student;
import static com.sombra.promotion.tables.Role.ROLE;
import static com.sombra.promotion.tables.User.USER;
import static com.sombra.promotion.tables.UserRole.USER_ROLE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@ComponentScan(basePackageClasses = {
        UserRoleRepository.class,
        RoleRepository.class,
        UserRepository.class
})
class UserRoleRepositoryTest {

    @Autowired
    private DSLContext ctx;

    @Autowired
    private UserRoleRepository repository;


    @Nested
    @DisplayName("Set role for user")
    class SetRoleForUser {

        @Test
        void must_set_one_role_for_user() {

            // given
            String username = "test-username";
            RoleEnum roleType = instructor;

            ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(username, "test-password", UUID.randomUUID())
                    .execute();

            // act
            repository.setRoleForUser(username, roleType);

            // fetch
            RoleEnum actual = ctx.select()
                    .from(USER)
                    .join(USER_ROLE).on(USER_ROLE.USER_ID.eq(USER.ID))
                    .join(ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                    .where(USER.USERNAME.eq(username))
                    .fetchAny(ROLE.NAME);


            // verify
            assertThat(actual, is(roleType));

        }

        @Test
        void must_do_nothing_when_user_already_has_this_role() {

            // given
            String username = "test-username";
            RoleEnum roleType = instructor;

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
            repository.setRoleForUser(username, roleType);

            // fetch
            List<RoleEnum> actual = ctx.select()
                    .from(USER)
                    .join(USER_ROLE).on(USER_ROLE.USER_ID.eq(USER.ID))
                    .join(ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                    .where(USER.USERNAME.eq(username))
                    .fetch(ROLE.NAME);


            // verify
            assertThat(actual, contains(is(roleType)));
        }

    }

    @Nested
    @DisplayName("Tests for user insert")
    class InsertUser {

        @Test
        void must_insert_user() {

            // given
            UUID salt = UUID.randomUUID();

            // act
            UUID userId = repository.insertUser("test-username1", "test-hashedPassword1", salt, student);

            // fetch
            User actual = ctx.select()
                    .from(USER)
                    .where(USER.ID.eq(userId))
                    .fetchSingle()
                    .into(User.class);

            // verify
            assertThat(actual, allOf(
                    hasProperty("username", is("test-username1")),
                    hasProperty("password", is("test-hashedPassword1")),
                    hasProperty("salt", is(salt)),
                    hasProperty("id", notNullValue())
            ));

        }

        @Test
        void must_insert_user_role() {

            // given
            UUID salt = UUID.randomUUID();

            // act
            UUID userId = repository.insertUser("test-username1", "test-hashedPassword1", salt, student);

            // fetch
            Record actual = ctx.select()
                    .from(USER_ROLE)
                    .join(ROLE)
                    .on(USER_ROLE.ROLE_ID
                            .eq(ROLE.ID)
                    ).where(USER_ROLE.USER_ID
                            .eq(userId)
                    ).fetchSingle();

            RoleEnum roleStudentName = actual.into(ROLE.NAME).component1();
            UUID userRoleUserId = actual.into(USER_ROLE.USER_ID).component1();

            // verify
            assertThat(roleStudentName, is(student));
            assertThat(userRoleUserId, is(userId));

        }

    }

}
