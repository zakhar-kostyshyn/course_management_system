package com.sombra.promotion.unit.service.common.manyToMany;

import com.sombra.promotion.dto.response.UserRoleResponse;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.mapper.UserRoleMapper;
import com.sombra.promotion.repository.manyToMany.UserRoleRepository;
import com.sombra.promotion.service.common.RoleService;
import com.sombra.promotion.service.common.manyToMany.UserRoleService;
import com.sombra.promotion.tables.pojos.Role;
import com.sombra.promotion.tables.pojos.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock UserRoleRepository userRoleRepository;
    @Mock
    UserRoleMapper userRoleMapper;
    @Mock RoleService roleService;
    @InjectMocks
    UserRoleService userRoleService;
    @InjectMocks @Spy UserRoleService spy;
    @Captor ArgumentCaptor<UserRole> userRoleCaptor;

    UUID userId;
    UUID roleId;
    RoleEnum roleEnum;
    Role role;
    UserRoleResponse response;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        roleId = UUID.randomUUID();
        role = mock(Role.class);
    }

    @Test
    void must_save_user_role_by_role_enum() {
        when(role.getId()).thenReturn(roleId);
        when(roleService.getRoleByRoleEnum(any())).thenReturn(role);
        doReturn(response).when(spy).saveUserRole(any(), any(UUID.class));

        var result = spy.saveUserRole(userId, roleEnum);

        verify(roleService).getRoleByRoleEnum(roleEnum);
        verify(spy).saveUserRole(userId, roleId);
        assertThat(result, sameInstance(response));
    }

    @Test
    void must_save_user_role_and_return_response() {
        when(userRoleMapper.build(any())).thenReturn(response);
        var result = userRoleService.saveUserRole(userId, roleId);
        verify(userRoleRepository).persist(userRoleCaptor.capture());
        assertThat(userRoleCaptor.getValue().getRoleId(), is(roleId));
        assertThat(userRoleCaptor.getValue().getUserId(), is(userId));
        verify(userRoleMapper).build(userRoleCaptor.capture());
        assertThat(userRoleCaptor.getValue().getRoleId(), is(roleId));
        assertThat(userRoleCaptor.getValue().getUserId(), is(userId));
        assertThat(result, sameInstance(response));
    }

}
