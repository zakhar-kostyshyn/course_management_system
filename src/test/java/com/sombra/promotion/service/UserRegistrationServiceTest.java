package com.sombra.promotion.service;


import com.sombra.promotion.controller.authentication.request.RegistrationUserRequest;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.repository.UserRoleRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.sombra.promotion.Constants.*;
import static com.sombra.promotion.enums.RoleEnum.student;
import static org.mockito.Mockito.*;

class UserRegistrationServiceTest {

    private final UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    private final UserRegistrationService userRegistrationService = new UserRegistrationService(
            userRoleRepository, userRepository, bCryptPasswordEncoder
    );

    @Nested
    class Register {

        @Test
        void must_hashed_password_set_student_and_save() {

            // setup
            var request = new RegistrationUserRequest(TEST_USERNAME, TEST_PASSWORD);
            when(bCryptPasswordEncoder.encode(anyString())).thenReturn(TEST_HASH_PASSWORD);

            // act
            userRegistrationService.register(request);

            // verify
            verify(userRoleRepository).insertUser(TEST_USERNAME, TEST_HASH_PASSWORD, student);

        }

    }

}