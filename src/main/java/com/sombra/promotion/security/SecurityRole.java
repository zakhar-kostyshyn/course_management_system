package com.sombra.promotion.security;

import com.sombra.promotion.tables.pojos.Role;
import org.springframework.security.core.GrantedAuthority;

public class SecurityRole implements GrantedAuthority {

    private final Role role;

    public SecurityRole(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return String.format("ROLE_%s", role.getName().getName().toUpperCase());
    }
}
