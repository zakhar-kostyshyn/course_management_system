package com.sombra.promotion.integration.database;

import com.sombra.promotion.integration.database.abstraction.DatabaseIntegrationTest;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.tables.pojos.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static helpers.Constants.TEST_PASSWORD;
import static helpers.Constants.TEST_USERNAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

class UserRepositoryTest extends DatabaseIntegrationTest {

    @Autowired private UserRepository userRepository;

    @Test
    void must_required_user_by_username() {

        // setup
        UUID userId = insert.user(TEST_USERNAME, TEST_PASSWORD);

        // act
        User user = userRepository.requiredByUsername(TEST_USERNAME);

        // verify
        assertThat(user.getUsername(), is(TEST_USERNAME));
        assertThat(user.getId(), is(userId));

    }

}
