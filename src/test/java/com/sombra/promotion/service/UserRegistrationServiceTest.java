package com.sombra.promotion.service;


import com.sombra.promotion.controller.authentication.request.RegistrationUserRequest;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.repository.UserRoleRepository;
import com.sombra.promotion.utils.Base64Helper;
import com.sombra.promotion.utils.UUIDHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.sombra.promotion.Constants.*;
import static com.sombra.promotion.enums.RoleEnum.student;
import static org.mockito.Mockito.*;

class UserRegistrationServiceTest {

    private final UserRoleRepository userRoleRepository = mock(UserRoleRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UUIDHelper uuidHelper = mock(UUIDHelper.class);
    private final Base64Helper base64Helper = mock(Base64Helper.class);
    private final UserRegistrationService userRegistrationService = new UserRegistrationService(
            userRoleRepository, userRepository, uuidHelper, base64Helper
    );

    @Nested
    class Register {

        @Test
        void must_hashed_password_set_student_and_save() {

            // setup
            var request = new RegistrationUserRequest(TEST_USERNAME, TEST_PASSWORD);
            when(uuidHelper.randomUUID()).thenReturn(TEST_SALT);
            when(base64Helper.encodeToString(anyString())).thenReturn(TEST_HASH_PASSWORD);

            // act
            userRegistrationService.register(request);

            // verify
            verify(uuidHelper).randomUUID();
            verify(base64Helper).encodeToString(TEST_PASSWORD + TEST_SALT);
            verify(userRoleRepository).insertUser(
                    TEST_USERNAME, TEST_HASH_PASSWORD, TEST_SALT, student
            );

        }

    }

}