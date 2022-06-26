package com.sombra.promotion.repository;

import com.sombra.promotion.enums.RoleEnum;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

import static com.sombra.promotion.enums.RoleEnum.student;
import static com.sombra.promotion.tables.Role.ROLE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@JooqTest
@ComponentScan(basePackageClasses = {
        RoleRepository.class
})
class RoleRepositoryTest {

    @Autowired
    private DSLContext ctx;

    @Autowired
    private RoleRepository repository;

    @Nested
    @DisplayName("Select role ID by role type")
    class SelectRoleIdByRoleType {

        @Test
        void must_select_roleID_by_role_type() {

            // setup
            RoleEnum givenRole = student;
            UUID givenStudentId = ctx.select(ROLE.ID)
                    .from(ROLE)
                    .where(ROLE.NAME.eq(student))
                    .fetchAny()
                    .component1();

            // act
            UUID actual = repository.selectRoleIDByRoleType(givenRole);

            // verify
            assertThat(actual, is(givenStudentId));

        }

    }

}
