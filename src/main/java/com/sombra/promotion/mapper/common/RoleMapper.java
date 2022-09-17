package com.sombra.promotion.mapper.common;

import com.sombra.promotion.dto.response.RoleResponse;
import com.sombra.promotion.abstraction.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.RoleRepository;
import com.sombra.promotion.tables.pojos.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapper extends AbstractResponseFactory<Role, RoleResponse, RoleRepository> {

    private final RoleRepository roleRepository;

    @Override
    public RoleRepository getRepository() {
        return roleRepository;
    }

    @Override
    public RoleResponse build(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .role(role.getName())
                .build();
    }

}
