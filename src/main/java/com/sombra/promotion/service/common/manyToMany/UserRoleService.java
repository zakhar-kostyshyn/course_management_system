package com.sombra.promotion.service.common.manyToMany;

import com.sombra.promotion.dto.response.RoleResponse;
import com.sombra.promotion.dto.response.UserRoleResponse;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.mapper.UserRoleMapper;
import com.sombra.promotion.repository.manyToMany.UserRoleRepository;
import com.sombra.promotion.service.common.RoleService;
import com.sombra.promotion.tables.pojos.Role;
import com.sombra.promotion.tables.pojos.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;
    private final RoleService roleService;

    public List<RoleResponse> getRoleResponsesByUserId(UUID userId) {
        return userRoleRepository.findByFirstId(userId).stream()
                .map(Role::getId)
                .map(roleService::getRoleById)
                .collect(toList());
    }

    public UserRoleResponse saveUserRole(UUID userId, RoleEnum roleEnum) {
        Role role = roleService.getRoleByRoleEnum(roleEnum);
        return saveUserRole(userId, role.getId());
    }

    public UserRoleResponse saveUserRole(UUID userId, UUID roleId) {
        UserRole userRole = createUserRole(userId, roleId);
        userRoleRepository.persist(userRole);
        return userRoleMapper.build(userRole);
    }

    private UserRole createUserRole(UUID userId, UUID roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }

}
