package com.sombra.promotion.security.service;

import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.repository.manyToMany.UserRoleRepository;
import com.sombra.promotion.security.model.SecurityAuthority;
import com.sombra.promotion.security.model.SecurityUser;
import com.sombra.promotion.tables.pojos.Role;
import com.sombra.promotion.tables.pojos.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.enums.RoleEnum.student;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityUserDetailsServiceTest {

    @Mock UserRepository userRepository;
    @Mock UserRoleRepository userRoleRepository;
    @InjectMocks SecurityUserDetailsService securityUserDetailsService;


    @Test
    void must_get_user_and_userRole() {

        // setup
        String username = "test-username";
        String password = "test-password";
        UUID userId = UUID.randomUUID();
        User user = new User(userId, username, password);
        when(userRepository.requiredByUsername(any())).thenReturn(user);
        when(userRoleRepository.findByFirstId(any())).thenReturn(emptyList());

        // act
        securityUserDetailsService.loadUserByUsername(username);

        // verify
        verify(userRepository).requiredByUsername(username);
        verify(userRoleRepository).findByFirstId(userId);

    }


    @Test
    void must_build_and_return_user_details_based_on_user_and_userRole() {

        // setup
        String username = "test-username";
        String password = "test-password";
        UUID userId = UUID.randomUUID();
        User user = new User(userId, username, password);
        when(userRepository.requiredByUsername(any())).thenReturn(user);

        Role role = new Role(UUID.randomUUID(), student);
        when(userRoleRepository.findByFirstId(any())).thenReturn(List.of(role));

        // act
        SecurityUser result = securityUserDetailsService.loadUserByUsername(username);

        // verify
        assertThat(result.getId(), is(userId));
        assertThat(result.getUsername(), is(username));
        assertThat(result.getPassword(), is(password));
        assertThat(result.getAuthorities().size(), is(1));

    }


}
