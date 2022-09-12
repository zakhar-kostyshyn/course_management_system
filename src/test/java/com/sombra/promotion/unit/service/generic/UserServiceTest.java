package com.sombra.promotion.unit.service.generic;

import com.sombra.promotion.dto.response.UserResponse;
import com.sombra.promotion.factory.generic.UserFactory;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.service.generic.UserService;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.tables.pojos.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository userRepository;
    @Mock UserFactory userFactory;
    @Mock UUIDUtil uuidUtil;
    @InjectMocks
    UserService userService;
    @Captor ArgumentCaptor<User> userCaptor;

    @Test
    void must_get_all_users_and_return_as_list_of_user_response() {

        // setup
        User user = mock(User.class);
        UserResponse response = mock(UserResponse.class);
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userFactory.build(any(User.class))).thenReturn(response);

        // act
        List<UserResponse> result = userService.getAllUsers();

        // verify
        verify(userRepository).findAll();
        verify(userFactory).build(user);
        assertThat(result, hasItems(sameInstance(response)));

    }

    @Test
    void must_save_user_build_and_return_response() {

        // setup
        String username = "test-username";
        String hashedPassword = "test-hashedPassword";
        UUID userId = UUID.randomUUID();
        UserResponse response = mock(UserResponse.class);
        when(uuidUtil.randomUUID()).thenReturn(userId);
        when(userFactory.build(any(User.class))).thenReturn(response);

        // act
        UserResponse result = userService.saveUser(username, hashedPassword);

        // verify
        verify(uuidUtil).randomUUID();
        verify(userRepository).persist(userCaptor.capture());
        verify(userFactory).build(userCaptor.capture());
        assertThat(userCaptor.getValue().getId(), is(userId));
        assertThat(userCaptor.getValue().getUsername(), is(username));
        assertThat(userCaptor.getValue().getPassword(), is(hashedPassword));
        assertThat(result, sameInstance(response));

    }

}