package com.sombra.promotion.mapper;

import com.sombra.promotion.dto.response.UserRoleResponse;
import com.sombra.promotion.mapper.common.RoleMapper;
import com.sombra.promotion.mapper.common.UserMapper;
import com.sombra.promotion.tables.pojos.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRoleMapper {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public UserRoleResponse build(UserRole userRole) {
        return UserRoleResponse.builder()
                .role(roleMapper.build(userRole.getRoleId()))
                .user(userMapper.build(userRole.getUserId()))
                .build();
    }

    public UserRoleResponse build(UUID userId, UUID roleId) {
        return UserRoleResponse.builder()
                .user(userMapper.build(userId))
                .role(roleMapper.build(roleId))
                .build();
    }

}
