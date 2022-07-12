package com.sombra.promotion.repository;

import com.sombra.promotion.testConfigs.TestHelpersConfiguration;
import com.sombra.promotion.testHelpers.SelectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static com.sombra.promotion.enums.RoleEnum.student;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@JooqTest
@Import(TestHelpersConfiguration.class)
@ComponentScan(basePackageClasses = {
        RoleRepository.class
})
class RoleRepositoryTest {

    @Autowired private RoleRepository repository;
    @Autowired private SelectUtils select;

    @Nested
    @DisplayName("Select role ID by role type")
    class SelectRoleIdByRoleType {

        @Test
        void must_select_roleID_by_role_type() {

            // setup
            UUID givenRoleId = select.roleId(student);

            // act
            UUID result = repository.selectRoleIDByRoleType(student);

            // verify
            assertThat(result, is(givenRoleId));

        }

    }

}
