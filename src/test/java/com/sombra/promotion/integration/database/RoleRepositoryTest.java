package com.sombra.promotion.integration.database;

import com.sombra.promotion.integration.database.abstraction.DatabaseIntegrationTest;
import com.sombra.promotion.repository.RoleRepository;
import com.sombra.promotion.tables.pojos.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sombra.promotion.enums.RoleEnum.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class RoleRepositoryTest extends DatabaseIntegrationTest {

    @Autowired private RoleRepository roleRepository;

    @Test
    void must_required_role_by_role_enum() {

        // act
        Role studentRole = roleRepository.requiredByRoleEnum(student);
        Role instructorRole = roleRepository.requiredByRoleEnum(instructor);
        Role adminRole = roleRepository.requiredByRoleEnum(admin);

        // verify
        assertThat(studentRole.getName(), is(student));
        assertThat(instructorRole.getName(), is(instructor));
        assertThat(adminRole.getName(), is(admin));

    }

}
