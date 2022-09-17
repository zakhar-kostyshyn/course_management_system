package com.sombra.promotion.unit.service.specific;

import com.sombra.promotion.dto.request.RegistrationUserRequest;
import com.sombra.promotion.dto.response.UserResponse;
import com.sombra.promotion.dto.response.UserRoleResponse;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.mapper.UserRoleMapper;
import com.sombra.promotion.repository.RoleRepository;
import com.sombra.promotion.service.common.UserService;
import com.sombra.promotion.service.common.manyToMany.UserRoleService;
import com.sombra.promotion.service.UserRegistrationService;
import com.sombra.promotion.tables.pojos.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRegistrationServiceTest {

    @Mock PasswordEncoder passwordEncoder;
    @Mock UserService userService;
    @Mock UserRoleService userRoleService;
    @Mock RoleRepository roleRepository;
    @Mock
    UserRoleMapper userRoleMapper;
    @InjectMocks
    UserRegistrationService userRegistrationService;


    @Test
    void must_create_user_with_hashed_password() {

        // setup
        String username = "test-username";
        String password = "test-password";
        String hashedPassword = "test-hashedPassword";
        RegistrationUserRequest request = new RegistrationUserRequest(username, password);
        when(passwordEncoder.encode(any())).thenReturn(hashedPassword);

        UUID userId = UUID.randomUUID();
        UserResponse userResponse = mock(UserResponse.class);
        when(userResponse.getUserId()).thenReturn(userId);
        when(userService.saveUser(anyString(), anyString())).thenReturn(userResponse);

        Role role = mock(Role.class);
        UUID roleId = UUID.randomUUID();
        when(role.getId()).thenReturn(roleId);
        when(roleRepository.requiredByRoleEnum(any())).thenReturn(role);

        // act
        userRegistrationService.register(request);

        // verify
        verify(passwordEncoder).encode(password);
        verify(userService).saveUser(username, hashedPassword);

    }


    @Test
    void must_assign_student_role_for_new_user() {

        // setup
        String username = "test-username";
        String password = "test-password";
        String hashedPassword = "test-hashedPassword";
        RegistrationUserRequest request = new RegistrationUserRequest(username, password);
        when(passwordEncoder.encode(any())).thenReturn(hashedPassword);

        UUID userId = UUID.randomUUID();
        UserResponse userResponse = mock(UserResponse.class);
        when(userResponse.getUserId()).thenReturn(userId);
        when(userService.saveUser(anyString(), anyString())).thenReturn(userResponse);

        Role role = mock(Role.class);
        UUID roleId = UUID.randomUUID();
        when(role.getId()).thenReturn(roleId);
        when(roleRepository.requiredByRoleEnum(any())).thenReturn(role);

        // act
        userRegistrationService.register(request);

        // verify
        verify(roleRepository).requiredByRoleEnum(RoleEnum.student);
        verify(userRoleService).saveUserRole(userId, roleId);

    }



    @Test
    void must_build_and_return_response() {

        // setup
        String username = "test-username";
        String password = "test-password";
        String hashedPassword = "test-hashedPassword";
        RegistrationUserRequest request = new RegistrationUserRequest(username, password);
        when(passwordEncoder.encode(any())).thenReturn(hashedPassword);

        UUID userId = UUID.randomUUID();
        UserResponse userResponse = mock(UserResponse.class);
        when(userResponse.getUserId()).thenReturn(userId);
        when(userService.saveUser(anyString(), anyString())).thenReturn(userResponse);

        Role role = mock(Role.class);
        UUID roleId = UUID.randomUUID();
        when(role.getId()).thenReturn(roleId);
        when(roleRepository.requiredByRoleEnum(any())).thenReturn(role);

        UserRoleResponse response = mock(UserRoleResponse.class);
        when(userRoleMapper.build(any(), any())).thenReturn(response);

        // act
        UserRoleResponse result = userRegistrationService.register(request);

        // verify
        verify(userRoleMapper).build(userId, roleId);
        assertThat(result, sameInstance(response));

    }


}
