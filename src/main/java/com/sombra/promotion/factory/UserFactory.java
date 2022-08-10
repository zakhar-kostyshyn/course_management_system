package com.sombra.promotion.factory;

import com.sombra.promotion.dto.response.UserResponse;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.repository.UserRoleRepository;
import com.sombra.promotion.tables.pojos.Role;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public UserResponse build(String username) {
        return build(userRepository.selectUserByUsername(username));
    }

    public UserResponse build(UUID id) {
        return build(userRepository.selectUserById(id));
    }

    public List<UserResponse> build(List<User> users) {
        return users.stream().map(this::build).collect(toList());
    }

    private UserResponse build(User user) {
        List<RoleEnum> roles = userRoleRepository.selectRolesByUsername(user.getUsername()).stream()
                .map(Role::getName)
                .collect(toList());
        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .roles(roles)
                .build();
    }

}
