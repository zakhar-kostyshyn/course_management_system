package com.sombra.promotion.controller;

import com.sombra.promotion.dto.response.RoleResponse;
import com.sombra.promotion.service.common.manyToMany.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sombra.promotion.service.util.statics.SecurityPrincipalUtil.authenticatedUserId;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRole {

    private final UserRoleService userRoleService;

    @GetMapping("/roles")
    public List<RoleResponse> getRolesForAuthenticatedUser() {
        return userRoleService.getRoleResponsesByUserId(authenticatedUserId());
    }

}
