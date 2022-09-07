package com.sombra.promotion.service.generic.manyToMany;

import com.sombra.promotion.dto.response.UserRoleResponse;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.factory.specific.UserRoleFactory;
import com.sombra.promotion.repository.manyToMany.UserRoleRepository;
import com.sombra.promotion.service.generic.RoleService;
import com.sombra.promotion.tables.pojos.Role;
import com.sombra.promotion.tables.pojos.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRoleFactory userRoleFactory;
    private final RoleService roleService;

    public UserRoleResponse saveUserRole(UUID userId, RoleEnum roleEnum) {
        Role role = roleService.getRoleByRoleEnum(roleEnum);
        return saveUserRole(userId, role.getId());
    }

    public UserRoleResponse saveUserRole(UUID userId, UUID roleId) {
        UserRole userRole = createUserRole(userId, roleId);
        userRoleRepository.persist(userRole);
        return userRoleFactory.build(userRole);
    }

    private UserRole createUserRole(UUID userId, UUID roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }

}
