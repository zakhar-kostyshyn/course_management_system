package com.sombra.promotion.factory.specific;

import com.sombra.promotion.dto.response.UserRoleResponse;
import com.sombra.promotion.factory.generic.RoleFactory;
import com.sombra.promotion.factory.generic.UserFactory;
import com.sombra.promotion.tables.pojos.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRoleFactory {

    private final UserFactory userFactory;
    private final RoleFactory roleFactory;

    public UserRoleResponse build(UserRole userRole) {
        return UserRoleResponse.builder()
                .role(roleFactory.build(userRole.getRoleId()))
                .user(userFactory.build(userRole.getUserId()))
                .build();
    }

    public UserRoleResponse build(UUID userId, UUID roleId) {
        return UserRoleResponse.builder()
                .user(userFactory.build(userId))
                .role(roleFactory.build(roleId))
                .build();
    }

}
