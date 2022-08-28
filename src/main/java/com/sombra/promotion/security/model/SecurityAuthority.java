package com.sombra.promotion.security.model;

import com.sombra.promotion.enums.RoleEnum;
import org.springframework.security.core.GrantedAuthority;

public class SecurityAuthority implements GrantedAuthority {

    private final RoleEnum role;

    public SecurityAuthority(RoleEnum role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + role.getLiteral().toUpperCase();
    }
}
