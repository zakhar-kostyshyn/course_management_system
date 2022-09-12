package com.sombra.promotion.unit.service.generic;

import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.repository.RoleRepository;
import com.sombra.promotion.service.generic.RoleService;
import com.sombra.promotion.tables.pojos.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock RoleRepository roleRepository;
    @InjectMocks
    RoleService roleService;

    @Test
    void must_get_role_by_roleEnum_and_return() {

        // setup
        RoleEnum roleEnum = RoleEnum.admin;
        Role role = mock(Role.class);
        when(roleRepository.requiredByRoleEnum(any())).thenReturn(role);

        // act
        Role result = roleService.getRoleByRoleEnum(roleEnum);

        // verify
        verify(roleRepository).requiredByRoleEnum(roleEnum);
        assertThat(result, sameInstance(role));

    }

}
