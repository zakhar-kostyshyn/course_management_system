package com.sombra.promotion.factory.generic;

import com.sombra.promotion.dto.response.RoleResponse;
import com.sombra.promotion.interfaces.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.RoleRepository;
import com.sombra.promotion.tables.pojos.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleFactory extends AbstractResponseFactory<Role, RoleResponse, RoleRepository> {

    private final RoleRepository roleRepository;

    @Override
    public RoleRepository getDao() {
        return roleRepository;
    }

    @Override
    public RoleResponse build(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .roleEnum(role.getName())
                .build();
    }

}
